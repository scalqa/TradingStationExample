package com.datamata.mkt; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _1_Account:
  self: Table =>

  new TheColumn[String]("", 15, _.status.isSubmitted) {
    valueView_:(v => "")
    style = \/
    new AskCell[Boolean](_.control.sell_*) {
      format_:(_ => "")
      style_:?(_.value_?.take(_ == true).map(_ => "-fx-background-color: rgb(97, 0, 67)":Fx.Style))
      mouseClicked_:((e, c) => if (e.button.isLeft) position_?.forval(p => p.control.sell_*() = !p.control.sell_*()))

      contextMenu_:((e, c) => {
        val o = c.row.position_?.map_?(_.bots.~.drop(_.status.isStopped).read_?)
        e.actions += Fx.Action("generateSell", o, () => o.forval(v => ??? /*v.logic.generateSell*/) )
      })

    }
    new TrdCell[String] {
      value_:(_.positionRow.accountRow.id_? or "?")
      styleClass = AccountIdClass
    }
    new BidCell[Boolean](_.control.buy_*) {
      format_:(_ => "")
      style_:?(_.value_?.take(_ == true).map(_ => "-fx-background-color: rgb(0, 0, 150)":Fx.Style))
      mouseClicked_:((e, c) => if (e.button.isLeft) position_?.forval(p => p.control.buy_*() = !p.control.buy_*()))
    }
  }

