package com.datamata.mkt; package app.trading_station; package positions; import Live.*; import language.implicitConversions

object Positions extends Ui.Module.Listing.Default[Row](new table.Table):
  type Row = positions.Row
  selection.mode = Fx.Selection.Mode.Multiple
  lazy  val selected_* = selection.property(\/).map_^(_.position)

  def addAll(en: Accounts.Row, v: ~[Symbol])                : Unit = v.foreach(add(en, _))
  def removeIfNotActive(row: Int)                           : Unit = if (this.apply(row).position.status.nonActive) rows.removeAt(row)
  def add(en: Accounts.Row, s: Symbol)                      : Unit = add(en, en.account_?.map(_.Positions.get(s)).get)
  def add(en: Accounts.Row, p: Position, index: Int = size) : Unit = synchronized {assert(en.account_?.contains(p.account));if (! ~.find_?(_.position == p)) Table.rows.addAt(index, new Row(en, p))}
  def addEmpty(i: Int)                                      : Unit = Table.rows.addAt(i, new Row(null, \/))
