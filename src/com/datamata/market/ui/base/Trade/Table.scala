package com.datamata; package market; package ui; package base; package Trade; import language.implicitConversions

import  Mkt.Live.{ Position => TP }

trait Table[ROW] extends Ui.Table[ROW]:
  type VIEW <: Mkt.Trade

  protected def setupDefaultColumns: Unit = { TradeSymbolColumn(); QntyColumn(); PriceColumn(); AmountIndexColumn(); PlColumn(); FillCountColumn() }

  class TradeSymbolColumn extends Column[Symbol]{       Ui.SymbolConfig(this); valueView_:(_.symbol) }
  class PriceColumn       extends Column[Price] {        Ui.PriceConfig(this); valueView_:*(_ match{ case v: TP => v.price_*;     case v => Pro.O.constant(v.price) })}
  class QntyColumn        extends Column[Qnty]  {         Ui.QntyConfig(this); valueView_:*(_ match{ case v: TP => v.qnty_*;      case v => Pro.O.constant(v.qnty) })}
  class PlColumn          extends Column[Amount]{           Ui.PlConfig(this); valueView_:*(_ match{ case v: TP => v.pl_*;        case v => Pro.O.constant(v.pl) })}
  class FillCountColumn   extends Column[Number]("Fills", 30) {                valueView_:(_.fills.size: Number) }

  class AmountIndexColumn extends Column[Number]("A") { Ui.IndexConfig(this);  valueView_:*(v => (v match{ case v: TP => v.amount_*; case v => Pro.O.constant(v.amount) }).map_^(v => (v / 1000D).Number))}


