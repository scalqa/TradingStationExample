package com.datamata.market; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

import Fx.*

object Table extends Fx.Table[Row] {
  type VIEW = Connection.Able;  useViewOpt(_.valueOpt)

  new Column[String]("Id", 12){
    useValueOpt(_.idOpt)
    styleClass = AccountIdClass
  }
  new Column[String]("Name", 100) {
    useValueOpt(_.valueOpt.map(_.name))
    alignment = LEFT
    new CustomCell[String](_ == Row.Header) {
      useValue(_ => "Total: ")
      alignment = RIGHT
      style = "-fx-text-fill: grey;"
    }
  }
  new Column[Amount] {
    PlConfig(this)
    useValueOptPro(_.accountOpt.map(_.plPro))
    new CustomCell[Amount](_ == Row.Header) {
      useValue(_ => rows.stream.mapOpt(_.accountOpt).map(_.pl).sum)
      PlConfig(this)
      refreshEvery(1.Second)
    }
  }
  new Column[String]("Connect", 50) {
    useValueFromViewOpt(_.??.take(_.connection.state.isConnected).map(_.connection.lastMessageTime).dropVoid.map(_.age.secondsTotal).map(s => if (s <= 5) "ok" else s.tag))
    refreshEvery(1.Second)
    style = "-fx-text-fill: grey;"
    useStyleOpt(_.viewOpt.take(_.connection.state.isLoading).map(_ => "-fx-background-color: green":Style))
    useStyleOpt(_.viewOpt.take(_.connection.state.isDisconnected).map(_ => "-fx-background-color: red":Style))
  }
  // ---------------------------------------------------------------------------------------------------
  new CustomRow(_ => true) {
    private def cleanUp = Messages.publishInfo(rows.stream.mapOpt(_.accountOpt).flatMap(_.orderStream).drop(_.status.isActive).peek(_.cancel).count +- " orders processed")

    useContextMenu((e, c) => e.actions ++= c.viewOpt.map(_.actionStream ++ Actions.stream(c.row)).or(Stream(Fx.Action("Clear all Non Active Orders ", () => cleanUp))))
  }
  // ---------------------------------------------------------------------------------------------------
  headerFooter = (Stream(Row.Header), VOID)
  sortMode     = Fx.Table.SortMode.ProxyWithUnsorted
}
