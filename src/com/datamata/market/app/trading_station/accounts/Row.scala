package com.datamata.market; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

class Row(id: Opt[String] = VOID, val valueOpt : Opt[Connection.Able] = VOID, isSub: Boolean = false) extends Able.Doc:

  val idOpt      : Opt[String]  = id
  def accountOpt : Opt[Account] = valueOpt.takeType[Account]
  def tapeOpt    : Opt[Tape]    = valueOpt.takeType[Tape]
  def doc       : Doc          = Doc(this) += ("id", idOpt)

  accountOpt.forval(a => {
    a.onPositionStatusChange((p, s, os) => if (s.isActive && os.nonActive) Positions.add(this, p))
    a.connection.connectedPro.onValueChange(b => if (b) a.Positions.stream.take(_.status.isActive).foreach(Positions.add(this, _)))
  })


object Row:
  object Header extends Row(VOID, VOID)

