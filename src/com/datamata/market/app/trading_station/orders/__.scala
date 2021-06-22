package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

import orders.*

object Orders extends Ui.Module.Listing[Row]:
  private val manual_* = Pro.M(false)

  object Table extends orders.table.Table

  Positions.selection.onChange(s => control() = s.get_? or \/)

  def position_? : Opt[Position] = control().^.?.map_?(_.position.^.?)

  def order(q: Qnty, p: Price): Unit = position_?.forval(pos => {
    manual_*() = true
    val o = pos.localOrder(Order.Quote(Order.Type.Lmt, q, p), Method.Manual)
    manual_*() = false
    Table.rows.addAt(Table.rows.~.findPosition_?(_.order_?.isVoid).get + (o.qnty.isShort ? 0 or 3), new Row(control(), o))
  })

  // ***************************************************************************************************
  private object control extends Val.Pro.OM.X.Basic[Positions.Row](\/) {

    this.onValueChangeWithOld((pr, or) => {
      reload
      or.position.localOrders.onAdd(Event.Id.cancel1(this))
      pr.position.localOrders.onAdd(Event.Id.make1(this, a => {
        if (!manual_*()) { val e = new Row(control(), a); rows.addAt(rows.orderedSearch(e).start, e) }
      })).cancelIf(() => control() != pr)
      or.position.localOrders.onRemove(Event.Id.cancel1(this))
      pr.position.localOrders.onRemove(Event.Id.make1(this, o => rows.~.findPosition_?(_.order_?.contains(o)).forval(rows.removeAt))).cancelIf(() => control() != pr)
    })

    def reload: Unit = control().^.?
       .fornil{Table.items.clear}
       .forval(r => Table.items.replaceAll((~~[Quote.Type](BID, LAST, ASK).map(new Row(r, _)) ++ r.position.localOrders.~.map(new Row(r, _))).sort))
  }

