package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _8_Expire:
  self: Table =>

  new TheColumn[Time]("Expire", 45, _.status.isActive) {
    TimeConfig(this)
    useValueOptPro(_.orderOpt.take(_.status.notClosed).map(_.expirePro))

    new BidCell() {
      useCellSetup(PositiveNegativePseudoGroup(_, -1))
    }
    new TrdCell(_.changePro, PriceChangeConfig)
    new AskCell() {
      useCellSetup(PositiveNegativePseudoGroup(_, -1))
    }

    useContextMenu((e, c) => c.viewOpt.take(_.status.notClosed).forval(o => {

      def tu = Stream.void[Time.Length] ++ Stream(1, 2, 5, 10, 15, 30).map(_.Minutes) ++ Stream(1, 2, 3).map(_.Hours)

      e.actions +=  Fx.Action("*", () => o.expire = VOID)
      e.actions ++= tu.map(t => Fx.Action(t.tag, () => o.expire = o.position.Tape.time + t + 1.Second))
    }))
  }

