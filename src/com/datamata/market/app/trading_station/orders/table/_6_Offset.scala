package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _6_Offset:
  self: Table =>

  new TheColumn[Price]("Offset", 45, _.status.isActive) {
    //    value_:(_ => \/)
    new TrdCell(_.Last.price_*, PriceConfig)
    new AskCell[String] {
      value_:(_ => "Sell")
      style = "-fx-text-fill: dimgrey"
      mouseClicked_:((e, c) => if (e.button.isLeft) position_?.forval(p => Orders.order(p.qnty.?.take(_ > 0).map(-_) or -100.Qnty, p.Ask.price)))
    }
    new BidCell[String] {
      value_:(_ => "Buy")
      style = "-fx-text-fill: dimgrey"
      mouseClicked_:((e, c) => if (e.button.isLeft) position_?.forval(p => Orders.order(p.qnty.?.take(_ < 0).map(_.abs) or 100.Qnty, p.Bid.price)))
    }
  }

