package com.datamata.mkt; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _8_Expire:
  self: Table =>

  new TheColumn[Time]("Expire", 45, _.status.isActive) {
    TimeConfig(this)
    value_:?*(_.order_?.take(_.status.notClosed).map(_.expire_*))

    new BidCell() {
      cell_:(PositiveNegativePseudoGroup(_, -1))
    }
    new TrdCell(_.change_*, PriceChangeConfig)
    new AskCell() {
      cell_:(PositiveNegativePseudoGroup(_, -1))
    }

    contextMenu_:((e, c) => c.view_?.take(_.status.notClosed).forval(o => {

      def tu = ~~.void[Time.Length] ++ ~~(1, 2, 5, 10, 15, 30).map(_.Minutes) ++ ~~(1, 2, 3).map(_.Hours)

      e.actions +=  Fx.Action("*", () => o.expire = \/)
      e.actions ++= tu.map(t => Fx.Action(t.tag, () => o.expire = o.position.Tape.time + t + 1.Second))
    }))
  }

