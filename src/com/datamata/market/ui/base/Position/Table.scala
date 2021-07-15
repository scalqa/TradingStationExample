package com.datamata; package market; package ui; package base; package position; import language.implicitConversions

import  Live.{ Position => LP }
import  Ui.*

trait Table[RAW] extends ticker.Table[RAW] with trade.Table[RAW]:
  type VIEW <: Position

  override protected def setupDefaultColumns: Unit =
    super.setupTickerColumns
    QntyColumn().reposition(1); PriceColumn().reposition(2); AmountIndexColumn().reposition(3); PaperPlColumn().reposition(4); PlColumn().reposition(5)

  class PaperPlColumn     extends Column[Amount]("PPL") {  PlConfig(this); valueView_:*(_ match{ case v: LP => v.paperPl_*;       case v => Pro.O.constant(v.paperPl) })}
  class BookedPlColumn    extends Column[Amount]("BPL") {  PlConfig(this); valueView_:*(_ match{ case v: LP => v.bookedPl_*;      case v => Pro.O.constant(v.bookedPl) })}

