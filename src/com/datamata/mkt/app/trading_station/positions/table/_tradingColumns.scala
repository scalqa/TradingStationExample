package com.datamata.mkt; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

transparent trait _tradingColumns:
  table: Table =>

  class OrderColumn(side: Side, isPrice: Boolean) extends Column[Order]() {
    if (!isPrice) {
      label = "Lots";
      prefWidth = 28;
      styleClass = MktLotsClass;
      format_:(_.remaining.lots.abs.format("##.##"), _ => "")
    } else {
      label = if (side.isLong) "Buy" else "Sell"
      prefWidth = 42
      styleClass = MktPriceClass
      format_:(_.price.abs.format("##.00#"), _ => "")
    }
    valueView_:*(v => v.localOrders.current_?*(side).map_^(_ or \/))
    style_:?(c => c.value_?.take(_.status.isPending).map(_ => "-fx-border-color: blue; -fx-border-width: 1px":Fx.Style)
      or_? c.row.position.localOrders.side(side).~.map(_.changed).max_?.map(_.age.secondsTotal).take(_ < (isPrice ? 60 or 10)).map(_ => "-fx-border-color: white; -fx-border-width: 1px":Fx.Style))
    refreshEvery(1.Second)
  }
  new OrderColumn(BUY, false)
  new OrderColumn(BUY, true)
  new BidLotsColumn
  new BidPriceColumn {
    updateTrigger_:(_.position.localOrders.currentBid_?*, _.position.localOrders.currentAsk_?*)
    style_:?(_.row_?.map(_.position).drop(_.Bid.price.isVoid).map_?(p => p.orders.~.take(_.isCoveredBy(p.ticker.Bid.price)).sortBy(_.price).reverse.read_?.map(_.background)))
  }
  new SpreadPercentColumn
  new AskPriceColumn {
    updateTrigger_:(_.position.localOrders.currentBid_?*, _.position.localOrders.currentAsk_?*)
    style_:?(_.row_?.map(_.position).drop(_.Ask.price.isVoid).map_?(p => p.orders.~.take(_.isCoveredBy(p.ticker.Ask.price)).sortBy(_.price).read_?.map(_.background)))
  }
  new AskLotsColumn
  new OrderColumn(SELL, true)
  new OrderColumn(SELL, false)
  new LastPriceColumn;
  new PriceChangeColumn
  new Column[Time] {
    TimeConfig(this)
    valueView_:*(_.created_*)
    label = \/
    sortable = false
    graphic = Fx.Label().^(l => {
      Fx.Thread.scheduleEvery(1.Second, l.text = DayTime().roundTo(1.Second)(using DOWN).tag)
      //        Table.ordering = Time.Ordering.reverse.on(_.position.changed)
      //      l.onMouseLeftClicked(_ => table.ordering = Time.Ordering.on(_.position.changed))
    })
  }

