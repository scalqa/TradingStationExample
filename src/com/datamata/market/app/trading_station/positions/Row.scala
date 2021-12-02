package com.datamata.market; package app.trading_station; package positions; import Live.*; import language.implicitConversions

class Row(val accountRow: Accounts.Row, val position: Live.Position) extends Able.Doc {

  def curOdrOpt(s: Side): Opt[Order] = position.??.mapOpt(_.localOrders.currentOptPro(s)().asInstanceOf[Opt[Order]])

  def doc = Doc(this) += ("account", accountRow.idOpt) += ("symbol", position.symbol)

}

object Row extends Gen.Void.Setup[Row](new Row(null, VOID) with Gen.Void)
