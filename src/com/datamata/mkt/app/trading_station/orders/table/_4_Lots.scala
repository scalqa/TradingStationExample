package com.datamata.mkt; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _4_Lots:
  self: Table =>

  new TheColumn[Qnty]("Lots", 45, o => true) {
    LotConfig(this)

    valueView_:*(_.remaining_*)

    edit_:(
      new TextField(_.toLong_??.map_??{
        case v if v <  0 => "Must be positive".Problem
        case v if v == 0 => "Cannot be zero".Problem
        case v           => v.Qnty.??
      }),
      (e, q) => e.order.qnty = q * 100 * (e.order.qnty.isShort ? -1 or 1),
      _.order_?.take(_.status.notClosed)
    )

    contextMenu_:((e, c) => c.view_?.take(_.status.notClosed).forval(o => {
      (-10 <> 10).~.map(o.lots.abs + _).take(_ > 0).foreach(l => e.actions += Fx.Action(l.format("#.#"), () => o.lots = l.sided(o.qnty.side)))
    }))

    styleClass = "dflt"

    new AskCell(_.Ask.qnty_*, LotConfig)
    new TrdCell(_.price_*,    PriceConfig){ format_:(_.^.?.map(Ui.Context.MathFormat.apply(_))) }
    new BidCell(_.Bid.qnty_*, LotConfig)
  }

