package com.datamata.market; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

class Table extends ui.base.position.Table[Row] with _positionColumns with _tradingColumns:
  type VIEW = Position; view_:(_.position)
  override protected def setupDefaultColumns = ()
  private val statusOrd =  summon[Ordering[Position.Status]].on[Row](_.position.status)

  onContextMenu(e => e.actions
    += Fx.Action("Clear All Non Active", items.~.exists(_.position.status.nonActive), () => (0 <>> items.size).~.reverse.foreach(Positions.removeIfNotActive))
    += Fx.CheckBox("Sort Status First", sortingBase.nonVoid).^(cb => cb.onActionRun(Table.this.sortingBase = if (cb.selected) statusOrd else \/))
  )

  new CustomRow(_ => true) {
    contextMenu_:((e, c) => {
      class OrdersMenu(sd: Side) extends Fx.Menu(sd.tag + " Orders") {
        def o_~ = selection.~.flatMap(_.position.localOrders).take(sd).take(_.status.isActive)
        enable = o_~.read_?
        this
          += Fx.Action("Cancel",       () => o_~.foreach(_.cancel))
          += Fx.Action("Set to last",  () => o_~.foreach(o => { o.price = o.ticker.Last.price; o.submit }))
          += Fx.Action("Set to trade", () => o_~.foreach(o => { o.price = o.ticker.quote(sd.reverse).price; o.submit }))
      }
      e.actions
        += Fx.Action("Remove", selection.~.exists(_.position.status.nonActive), () => selection.indexes.~.load.reverse.foreach(Positions.removeIfNotActive))
        += new OrdersMenu(BUY)
        += new OrdersMenu(SELL)
        += Fx.Action("Generate Sells", () => selection.~.map(_.position).take(_.qnty > 0).take(_.orders.isEmpty).map_?(_.bots.~.drop(_.status.isStopped).read_?).map_?(_.logic_?).foreach(_.sell.generateSell))
        += SEPARATOR
    })
    mouseClicked_:((e, c) => {
      if (e.button.isLeft && !c.row_?) selection.clear
      if (e.button.isLeft && e.clickCount == 3) c.row_?.forval(p => println(p))
    })
  }

  sortingBase = statusOrd
  sortMode = Fx.Table.SortMode.Direct

