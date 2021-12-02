package com.datamata.market; package app.trading_station; package bots; import Live.*; import language.implicitConversions

object Table extends Ui.Table[Live.Account.Bot]:

  new Column[String]("Method", 60, _.method.id) {
    alignment = LEFT
  }

  new Column[T.Parameter]("Param", 50) {
    useValuePro(_.parameterPro)
    useFormat(_.tagBrief)
    useContextMenu((e, c) => c.rowOpt.forval(m => {
      class Menu(name: String, f: T.Setting => Unit) extends Fx.Menu(name) { T.Setting.stream.foreach(s => items += Fx.Menu.Item(s.ordinal +- s, ae => f(s))) }
      e.actions
        += new Menu("Buy Setting",  s => m.parameter = m.parameter.copyBuy(s))
        += new Menu("Sell Setting", s => m.parameter = m.parameter.copySell(s))
        += new Fx.Menu.Item.Custom(Fx.Pane(
          "Amount K. ",
          Fx.Text.Field.Number(6, (m.parameter.amount / 1000).toInt.tag).self(v => v.onActionRun( v.intResult.forval(i => m.parameter = m.parameter.copyAmount((i * 1000).Amount))))))
        += new Fx.Menu.Item.Custom(Fx.Pane(
          "max Positions ",
          Fx.Text.Field.Number(6, m.parameter.count.tag).self(v => v.onActionRun(v.intResult.forval(i => m.parameter = m.parameter.copyCount(if (i <= 0 || i >= 1000000) 1000000 else i))))))
      })
    )
  }

  new Column[String] {
    useValue(v => { val (x,y,z) = v.positionBots.stream.map(_.status).countFew(_.isTrading, _.isLiquidating, _.isStopped); "" + x + " : " + y + " : " + z})

    graphic = Fx.Label("Status").self(_.tooltip = "Trading : Liquidating : Stopped")

    refreshEvery(1.Second)

    useContextMenu((e, c) => c.rowOpt.forval(aBot => {
      val sz = aBot.account.Tape.Tickers.size
      val trd = aBot.positionBots.stream.map(_.status).take(_.isTrading).count
      e.actions ++= Stream(
        Fx.Action("Start - " + sz, sz > 0, () => aBot.start),
        Fx.Action("Liquidate - ", () => aBot.liquidate),
        Fx.Action("Stop - " + trd, trd > 0, () => aBot.stop))
    }))
  }

  new Column[Long]("Tests/Sec", 55) {
    useValuePro(_.evalsCountPro)
  }

  sortMode = Fx.Table.SortMode.ProxyWithUnsorted


