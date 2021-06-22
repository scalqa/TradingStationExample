package com.datamata.market; package app.trading_station; package positions; import Live.*; import language.implicitConversions

class Row(val accountRow: Accounts.Row, val position: Live.Position) extends Able.Doc {

  def curOdr_?(s: Side): Opt[Order] = position.^.?.map_?(_.localOrders.current_?*(s)().asInstanceOf[Opt[Order]])

  def doc = Doc(this) += ("account", accountRow.id_?) += ("symbol", position.symbol)

}

object Row extends Gen.Void.Setup[Row](new Row(null, \/) with Gen.Void)
