package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

import orders.*

object Orders extends Ui.Module.Listing[Row]:
  private val manualPro = Pro.M(false)

  object Table extends orders.table.Table

  Positions.selection.onChange(s => control() = s.valueOpt or VOID)

  def positionOpt : Opt[Position] = control().??.mapOpt(_.position.??)

  def order(q: Qnty, p: Price): Unit = positionOpt.forval(pos => {
    manualPro() = true
    val o = pos.localOrder(Order.Quote(Order.Type.Lmt, q, p), Method.Manual)
    manualPro() = false
    Table.rows.addAt(Table.rows.stream.findPositionOpt(_.orderOpt.isVoid).get + (o.qnty.isShort ? 0 or 3), new Row(control(), o))
  })

  // ***************************************************************************************************
  private object control extends Val.Pro.OM.X.Basic[Positions.Row](VOID) {

    this.onValueChangeWithOld((pr, or) => {
      reload
      or.position.localOrders.onAdd(Event.Id.cancel1(this))
      pr.position.localOrders.onAdd(Event.Id.make1(this, a => {
        if (!manualPro()) { val e = new Row(control(), a); rows.addAt(rows.orderedSearch(e).start, e) }
      })).cancelIf(() => control() != pr)
      or.position.localOrders.onRemove(Event.Id.cancel1(this))
      pr.position.localOrders.onRemove(Event.Id.make1(this, o => rows.stream.findPositionOpt(_.orderOpt.contains(o)).forval(rows.removeAt))).cancelIf(() => control() != pr)
    })

    def reload: Unit = control().??
       .fornil{Table.items.clear}
       .forval(r => Table.items.replaceWith((Stream[Quote.Type](BID, LAST, ASK).map(new Row(r, _)) ++ r.position.localOrders.stream.map(new Row(r, _))).sort))
  }

