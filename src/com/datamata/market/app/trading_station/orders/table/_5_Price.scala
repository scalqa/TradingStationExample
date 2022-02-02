package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _5_Price:
  self: Table =>

  new TheColumn[Price]("Price", 45, o => true) {
    PriceConfig(this)
    useValueFromViewPro(_.pricePro);

    useEditor(
      new TextField(_.toDoubleResult.mapResult {
        case v if v < 0  => Problem("Must be positive")
        case v if v == 0 => Problem("Cannot be zero")
        case v           => v.Price
      }),
      _.order.price = _,
      _.orderOpt.take(_.status.notClosed))

    onCellChange(_ => refreshColumn)

    useContextMenu((e, c) => c.viewOpt.take(_.status.notClosed).forval(o =>
      e.actions ++= {
        (0.99 <> 1.01).stepStream(_ + 0.001).map(o.price * _).map(_.roundToDecimal(2)).take(_.toDouble >= 0.001).map(p => (p, Fx.Action(p.tag, () => o.price = p))) +
          { val p = o.position.ticker.Ask.price; (p, Fx.Action("ask".label, () => o.price = p)) } +
          { val p = o.position.ticker.Bid.price; (p, Fx.Action("bid".label, () => o.price = p)) }
      }.sortBy(_._1).reverse.map(_._2)))

    new AskCell(_.ticker.Ask.pricePro, PriceConfig) {
      useStyleOpt(_.row.positionOpt.mapOpt(p => p.localOrders.stream.take(o => o.status.notClosed && o.isCoveredBy(p.Ask.price)).sortBy(_.price).readOpt.map(_.background)))
    }
    new TrdCell[Number](_.ticker.spreadPro.mapView(_.toFloat.toNumber), IndexConfig)
    new BidCell(_.ticker.Bid.pricePro, PriceConfig) {
      useStyleOpt(_.row.positionOpt.mapOpt(p => p.localOrders.stream.take(o => o.status.notClosed && o.isCoveredBy(p.Bid.price)).sortBy(_.price).reverse.readOpt.map(_.background)))
    }

    graphic = Fx.Button("Price", rows.sort)
  }

