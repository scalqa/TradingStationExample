package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _4_Lots:
  self: Table =>

  new TheColumn[Qnty]("Lots", 45, o => true) {
    LotConfig(this)

    useValueFromViewPro(_.remainingPro)

    useEditor(
      new TextField(_.toLongResult.mapResult{
        case v if v <  0 => "Must be positive".Problem
        case v if v == 0 => "Cannot be zero".Problem
        case v           => Result(v.Qnty)
      }),
      (e, q) => e.order.qnty = q * 100 * (e.order.qnty.isShort ? -1 or 1),
      _.orderOpt.take(_.status.notClosed)
    )

    useContextMenu((e, c) => c.viewOpt.take(_.status.notClosed).forval(o => {
      (-10 <> 10).stream.map(o.lots.abs + _).take(_ > 0).foreach(l => e.actions += Fx.Action(l.format("#.#"), () => o.lots = l.sided(o.qnty.side)))
    }))

    styleClass = "dflt"

    new AskCell(_.Ask.qntyPro, LotConfig)
    new TrdCell(_.pricePro,    PriceConfig){ useFormat(_.??.map(Ui.Context.MathFormat.apply(_))) }
    new BidCell(_.Bid.qntyPro, LotConfig)
  }

