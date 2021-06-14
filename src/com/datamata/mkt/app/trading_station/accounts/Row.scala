package com.datamata.mkt; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

class Row(id: Opt[String] = \/, val value_? : Opt[Connection.Able] = \/, isSub: Boolean = false) extends Able.Doc:

  val id_?      : Opt[String]  = id
  def account_? : Opt[Account] = value_?.takeType[Account]
  def tape_?    : Opt[Tape]    = value_?.takeType[Tape]
  def doc       : Doc          = Doc(this) += ("id", id_?)

  account_?.forval(a => {
    a.onPositionStatusChange((p, s, os) => if (s.isActive && os.nonActive) Positions.add(this, p))
    a.connection.connected_*.onValueChange(b => if (b) a.Positions.~.take(_.status.isActive).foreach(Positions.add(this, _)))
  })


object Row:
  object Header extends Row(\/, \/)

