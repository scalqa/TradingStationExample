package com.datamata.market; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

class Table extends ui.base.position.Table[Row] with _positionColumns with _tradingColumns:
  type VIEW = Position; useView(_.position)
  override protected def setupDefaultColumns = ()
  private val statusOrd =  summon[Ordering[Position.Status]].on[Row](_.position.status)

  useContextMenu(e => e.actions
    += Fx.Action("Clear All Non Active", items.stream.exists(_.position.status.nonActive), () => (0 <>> items.size).stream.reverse.foreach(Positions.removeIfNotActive))
    += Fx.CheckBox("Sort Status First", sortingBase.nonVoid).self(cb => cb.onActionRun(Table.this.sortingBase = if (cb.selected) statusOrd else VOID))
  )

  new CustomRow(_ => true) {
    useContextMenu((e, c) => {
      class OrdersMenu(sd: Side) extends Fx.Menu(sd.tag + " Orders") {
        def oStream = selection.stream.flatMap(_.position.localOrders).take(sd).take(_.status.isActive)
        enable = oStream.readOpt
        this
          += Fx.Action("Cancel",       () => oStream.foreach(_.cancel))
          += Fx.Action("Set to last",  () => oStream.foreach(o => { o.price = o.ticker.Last.price; o.submit }))
          += Fx.Action("Set to trade", () => oStream.foreach(o => { o.price = o.ticker.quote(sd.reverse).price; o.submit }))
      }
      e.actions
        += Fx.Action("Remove", selection.stream.exists(_.position.status.nonActive), () => selection.indexes.stream.load.reverse.foreach(Positions.removeIfNotActive))
        += new OrdersMenu(BUY)
        += new OrdersMenu(SELL)
        += Fx.Action("Generate Sells", () => selection.stream.map(_.position).take(_.qnty > 0).take(_.orders.isEmpty).mapOpt(_.bots.stream.drop(_.status.isStopped).readOpt).mapOpt(_.logicOpt).foreach(_.sell.generateSell))
        += SEPARATOR
    })
    useMouseClicked((e, c) => {
      if (e.button.isLeft && !c.rowOpt) selection.clear
      if (e.button.isLeft && e.clickCount == 3) c.rowOpt.forval(p => println(p))
    })
  }

  sortingBase = statusOrd
  sortMode = Fx.Table.SortMode.Direct

