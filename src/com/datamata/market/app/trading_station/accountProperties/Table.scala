package com.datamata.market; package app.trading_station; package accountProperties; import Live.*; import language.implicitConversions

object Table extends Ui.Table[Able.Name & Pro.O[Any]]:

  new Column[String]("Name", 90, _.name) {
    alignment = LEFT
    style = "-fx-font-size:10"
  }

  new Column[Any]("Value", 75)(using Ordering.anyAsString) {
    useValuePro(v => v)
    useFormat(a => a.?.takeType[Amount].map(_.format("#,##0")) or a.tag)
    alignment = RIGHT
    useStyleClassOpt(_.rowOpt.map(_()).takeType[Amount].map(_ => MktAmountClass))
    style = "-fx-font-size:11"
  }

  sortMode = Fx.Table.SortMode.ProxyWithUnsorted

