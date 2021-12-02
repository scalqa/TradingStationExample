package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

import positions.*

object Positions extends Ui.Module.Listing.Default[Row](new table.Table):
  type Row = positions.Row
  selection.mode = Fx.Selection.Mode.Multiple
  lazy  val selectedPro = selection.property(VOID).mapView(_.position)

  def addAll(en: Accounts.Row, v: ~[Symbol])                : Unit = v.foreach(add(en, _))
  def removeIfNotActive(row: Int)                           : Unit = if (this.apply(row).position.status.nonActive) rows.removeAt(row)
  def add(en: Accounts.Row, s: Symbol)                      : Unit = add(en, en.accountOpt.map(_.Positions.get(s)).get)
  def add(en: Accounts.Row, p: Position, index: Int = size) : Unit = synchronized {assert(en.accountOpt.contains(p.account));if (! stream.findOpt(_.position == p)) Table.rows.addAt(index, new Row(en, p))}
  def addEmpty(i: Int)                                      : Unit = Table.rows.addAt(i, new Row(null, VOID))
