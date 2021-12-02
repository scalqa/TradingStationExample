package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _1_Account:
  self: Table =>

  new TheColumn[String]("", 15, _.status.isSubmitted) {
    useValueFromView(v => "")
    style = VOID
    new AskCell[Boolean](_.control.sellPro) {
      useFormat(_ => "")
      useStyleOpt(_.valueOpt.take(_ == true).map(_ => "-fx-background-color: rgb(97, 0, 67)":Fx.Style))
      useMouseClicked((e, c) => if (e.button.isLeft) positionOpt.forval(p => p.control.sellPro() = !p.control.sellPro()))

      useContextMenu((e, c) => {
        val o = c.row.positionOpt.mapOpt(_.bots.stream.drop(_.status.isStopped).readOpt)
        e.actions += Fx.Action("generateSell", o, () => o.forval(v => ??? /*v.logic.generateSell*/) )
      })

    }
    new TrdCell[String] {
      useValue(_.positionRow.accountRow.idOpt or "?")
      styleClass = AccountIdClass
    }
    new BidCell[Boolean](_.control.buyPro) {
      useFormat(_ => "")
      useStyleOpt(_.valueOpt.take(_ == true).map(_ => "-fx-background-color: rgb(0, 0, 150)":Fx.Style))
      useMouseClicked((e, c) => if (e.button.isLeft) positionOpt.forval(p => p.control.buyPro() = !p.control.buyPro()))
    }
  }

