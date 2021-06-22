package com.datamata.market; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

import Fx.*

object Table extends Fx.Table[Row] {
  type VIEW = Connection.Able;  view_:?(_.value_?)

  new Column[String]("Id", 12){
    value_:?(_.id_?)
    styleClass = AccountIdClass
  }
  new Column[String]("Name", 100) {
    value_:?(_.value_?.map(_.name))
    alignment = LEFT
    new CustomCell[String](_ == Row.Header) {
      value_:(_ => "Total: ")
      alignment = RIGHT
      style = "-fx-text-fill: grey;"
    }
  }
  new Column[Amount] {
    PlConfig(this)
    value_:?*(_.account_?.map(_.pl_*))
    new CustomCell[Amount](_ == Row.Header) {
      value_:(_ => rows.~.map_?(_.account_?).map(_.pl).sum)
      PlConfig(this)
      refreshEvery(1.Second)
    }
  }
  new Column[String]("Connect", 50) {
    valueView_:?(_.^.?.take(_.connection.state.isConnected).map(_.connection.lastMessageTime).dropVoid.map(_.age.secondsTotal).map(s => if (s <= 5) "ok" else s.tag))
    refreshEvery(1.Second)
    style = "-fx-text-fill: grey;"
    style_:?(_.view_?.take(_.connection.state.isLoading).map(_ => "-fx-background-color: green":Style))
    style_:?(_.view_?.take(_.connection.state.isDisconnected).map(_ => "-fx-background-color: red":Style))
  }
  // ---------------------------------------------------------------------------------------------------
  new CustomRow(_ => true) {
    private def cleanUp = Messages.publishInfo(rows.~.map_?(_.account_?).flatMap(_.order_~).drop(_.status.isActive).peek(_.cancel).count +- " orders processed")

    contextMenu_:((e, c) => e.actions ++= c.view_?.map(_.action_~ ++ Actions.~(c.row)).or(~~(Fx.Action("Clear all Non Active Orders ", () => cleanUp))))
  }
  // ---------------------------------------------------------------------------------------------------
  headerFooter = (~~(Row.Header), \/)
  sortMode     = Fx.Table.SortMode.ProxyWithUnsorted
}
