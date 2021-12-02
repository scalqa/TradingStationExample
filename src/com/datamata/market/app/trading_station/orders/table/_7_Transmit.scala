package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _7_Transmit:
  self: Table =>

  new TheColumn[String]("Trmt", 45, o => true) {
    new AskCell(_.highPro, PriceConfig)
    new BidCell(_.lowPro, PriceConfig)

    useValueFromViewPro(o => o.filledPro.mixView(o.statusPro,(v, s) => if (s.isSubmittable) "T" else v.lots.abs.??.map(_.toString) or ""))

    useStyleOpt(_.viewOpt.take(_.status.isSubmittable).map(_ => "-fx-text-fill: red;-fx-background-color: yellow;":Fx.Style))

    useMouseClicked((e, c) => if (e.button.isLeft) c.viewOpt.take(_.status.isSubmittable).forval(_.submit))

    //      new TheColumn[String]("Dest", 45, o => false) {onTradeRow(PRICE_SETUP, (r, p) = p.close());
  }

