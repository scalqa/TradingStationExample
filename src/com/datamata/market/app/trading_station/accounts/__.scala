package com.datamata.market; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

object Accounts extends Ui.Module.Listing.Default[Row](Table):
  inline def Row = accounts.Row; type Row = accounts.Row

  def add(id: String, c: Account | Tape, isSub: Boolean = false) = Table.rows += new Row(id.^.?, c.asInstanceOf[Connection.Able], isSub)

