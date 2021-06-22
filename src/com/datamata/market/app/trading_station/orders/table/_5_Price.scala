package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _5_Price:
  self: Table =>

  new TheColumn[Price]("Price", 45, o => true) {
    PriceConfig(this)
    valueView_:*(_.price_*);

    edit_:(
      new TextField(_.toDouble_??.map_?? {
        case v if v < 0  => Problem("Must be positive")
        case v if v == 0 => Problem("Cannot be zero")
        case v           => v.Price
      }),
      _.order.price = _,
      _.order_?.take(_.status.notClosed))

    onCellChange(_ => refreshColumn)

    contextMenu_:((e, c) => c.view_?.take(_.status.notClosed).forval(o =>
      e.actions ++= {
        (0.99 <> 1.01).step_~(_ + 0.001).map(o.price * _).map(_.roundDecimal(2)).take(_.Double >= 0.001).map(p => (p, Fx.Action(p.tag, () => o.price = p))) +
          { val p = o.position.ticker.Ask.price; (p, Fx.Action("ask".label, () => o.price = p)) } +
          { val p = o.position.ticker.Bid.price; (p, Fx.Action("bid".label, () => o.price = p)) }
      }.sortBy(_._1).reverse.map(_._2)))

    new AskCell(_.ticker.Ask.price_*, PriceConfig) {
      style_:?(_.row.position_?.map_?(p => p.localOrders.~.take(o => o.status.notClosed && o.isCoveredBy(p.Ask.price)).sortBy(_.price).read_?.map(_.background)))
    }
    new TrdCell[Number](_.ticker.spread_*.map_^(_.Float.Number), IndexConfig)
    new BidCell(_.ticker.Bid.price_*, PriceConfig) {
      style_:?(_.row.position_?.map_?(p => p.localOrders.~.take(o => o.status.notClosed && o.isCoveredBy(p.Bid.price)).sortBy(_.price).reverse.read_?.map(_.background)))
    }

    graphic = Fx.Button("Price", rows.sort)
  }

