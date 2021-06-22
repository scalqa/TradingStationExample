package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _7_Transmit:
  self: Table =>

  new TheColumn[String]("Trmt", 45, o => true) {
    new AskCell(_.high_*, PriceConfig)
    new BidCell(_.low_*, PriceConfig)

    valueView_:*(o => o.filled_*.mix_^(o.status_*,(v, s) => if (s.isSubmittable) "T" else v.lots.abs.^.?.map(_.toString) or ""))

    style_:?(_.view_?.take(_.status.isSubmittable).map(_ => "-fx-text-fill: red;-fx-background-color: yellow;":Fx.Style))

    mouseClicked_:((e, c) => if (e.button.isLeft) c.view_?.take(_.status.isSubmittable).forval(_.submit))

    //      new TheColumn[String]("Dest", 45, o => false) {onTradeRow(PRICE_SETUP, (r, p) = p.close());
  }

