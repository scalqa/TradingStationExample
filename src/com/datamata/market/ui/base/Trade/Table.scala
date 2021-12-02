package com.datamata; package market; package ui; package base; package trade; import language.implicitConversions

import  Mkt.Live.{ Position => TP }

trait Table[ROW] extends Ui.Table[ROW]:
  type VIEW <: Mkt.Trade

  protected def setupDefaultColumns: Unit = { TradeSymbolColumn(); QntyColumn(); PriceColumn(); AmountIndexColumn(); PlColumn(); FillCountColumn() }

  class TradeSymbolColumn extends Column[Symbol]{       Ui.SymbolConfig(this); useValueFromView(_.symbol) }
  class PriceColumn       extends Column[Price] {        Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: TP => v.pricePro;     case v => Pro.O.constant(v.price) })}
  class QntyColumn        extends Column[Qnty]  {         Ui.QntyConfig(this); useValueFromViewPro(_ match{ case v: TP => v.qntyPro;      case v => Pro.O.constant(v.qnty) })}
  class PlColumn          extends Column[Amount]{           Ui.PlConfig(this); useValueFromViewPro(_ match{ case v: TP => v.plPro;        case v => Pro.O.constant(v.pl) })}
  class FillCountColumn   extends Column[Number]("Fills", 30) {                useValueFromView(_.fills.size: Number) }

  class AmountIndexColumn extends Column[Number]("A") { Ui.IndexConfig(this);  useValueFromViewPro(v => (v match{ case v: TP => v.amountPro; case v => Pro.O.constant(v.amount) }).mapView(v => (v / 1000D).toNumber))}


