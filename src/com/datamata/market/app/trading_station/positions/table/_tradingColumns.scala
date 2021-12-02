package com.datamata.market; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

transparent trait _tradingColumns:
  table: Table =>

  class OrderColumn(side: Side, isPrice: Boolean) extends Column[Order]() {
    if (!isPrice) {
      label = "Lots";
      prefWidth = 28;
      styleClass = MktLotsClass;
      useFormat(_.remaining.lots.abs.format("##.##"), _ => "")
    } else {
      label = if (side.isLong) "Buy" else "Sell"
      prefWidth = 42
      styleClass = MktPriceClass
      useFormat(_.price.abs.format("##.00#"), _ => "")
    }
    useValueFromViewPro(v => v.localOrders.currentOptPro(side).mapView(_ or VOID))
    useStyleOpt(c => c.valueOpt.take(_.status.isPending).map(_ => "-fx-border-color: blue; -fx-border-width: 1px":Fx.Style)
      orOpt c.row.position.localOrders.side(side).stream.map(_.changed).maxOpt.map(_.age.secondsTotal).take(_ < (isPrice ? 60 or 10)).map(_ => "-fx-border-color: white; -fx-border-width: 1px":Fx.Style))
    refreshEvery(1.Second)
  }
  new OrderColumn(BUY, false)
  new OrderColumn(BUY, true)
  new BidLotsColumn
  new BidPriceColumn {
    refreshOn(_.position.localOrders.currentBidOptPro, _.position.localOrders.currentAskOptPro)
    useStyleOpt(_.rowOpt.map(_.position).drop(_.Bid.price.isVoid).mapOpt(p => p.orders.stream.take(_.isCoveredBy(p.ticker.Bid.price)).sortBy(_.price).reverse.readOpt.map(_.background)))
  }
  new SpreadPercentColumn
  new AskPriceColumn {
    refreshOn(_.position.localOrders.currentBidOptPro, _.position.localOrders.currentAskOptPro)
    useStyleOpt(_.rowOpt.map(_.position).drop(_.Ask.price.isVoid).mapOpt(p => p.orders.stream.take(_.isCoveredBy(p.ticker.Ask.price)).sortBy(_.price).readOpt.map(_.background)))
  }
  new AskLotsColumn
  new OrderColumn(SELL, true)
  new OrderColumn(SELL, false)
  new LastPriceColumn;
  new PriceChangeColumn
  new Column[Time] {
    TimeConfig(this)
    useValueFromViewPro(_.createdPro)
    label = VOID
    sortable = false
    graphic = Fx.Label().self(l => Fx.Thread.scheduleEvery(1.Second, l.text = DayTime.current.roundTo(1.Second)(using DOWN).tag))
  }

