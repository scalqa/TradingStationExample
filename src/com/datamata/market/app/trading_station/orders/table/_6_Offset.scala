package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _6_Offset:
  self: Table =>

  new TheColumn[Price]("Offset", 45, _.status.isActive) {
    //    useValue(_ => VOID)
    new TrdCell(_.Last.pricePro, PriceConfig)
    new AskCell[String] {
      useValue(_ => "Sell")
      style = "-fx-text-fill: dimgrey"
      useMouseClicked((e, c) => if (e.button.isLeft) positionOpt.forval(p => Orders.order(p.qnty.?.take(_ > 0).map(-_) or -100.Qnty, p.Ask.price)))
    }
    new BidCell[String] {
      useValue(_ => "Buy")
      style = "-fx-text-fill: dimgrey"
      useMouseClicked((e, c) => if (e.button.isLeft) positionOpt.forval(p => Orders.order(p.qnty.?.take(_ < 0).map(_.abs) or 100.Qnty, p.Bid.price)))
    }
  }

