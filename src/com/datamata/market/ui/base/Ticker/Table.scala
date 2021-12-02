package com.datamata; package market; package ui; package base; package ticker; import language.implicitConversions

import  Live.{ Ticker => LT }

trait Table[RAW] extends Ui.Table[RAW]:
  type VIEW <: M.Ticker

  protected def setupDefaultColumns: Unit = setupTickerColumns;

  private[market] def setupTickerColumns: Unit =
    SymbolColumn(); OpenColumn(); HighColumn(); LowColumn(); VolumeColumn(); BidLotsColumn(); BidPriceColumn();
    SpreadPercentColumn(); AskPriceColumn(); AskLotsColumn(); LastPriceColumn(); PriceChangeColumn()

  class SymbolColumn      extends Column[Symbol] {       Ui.SymbolConfig(this);  useValueFromView(_.symbol) }
  class OpenColumn        extends Column[Price]("Open") { Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.openPro;       case v => Pro.O.constant(v.open) })}
  class HighColumn        extends Column[Price]("High") { Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.highPro;       case v => Pro.O.constant(v.high) })}
  class LowColumn         extends Column[Price]("Low")  { Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.lowPro;        case v => Pro.O.constant(v.low) })}
  class CloseColumn       extends Column[Price]("Close") {Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.closePro;      case v => Pro.O.constant(v.close) })}
  class VolumeColumn      extends Column[Qnty] {         Ui.VolumeConfig(this); useValueFromViewPro(_ match{ case v: LT => v.volumePro;     case v => Pro.O.constant(v.volume) })}
  class BidLotsColumn     extends Column[Qnty] {            Ui.LotConfig(this); useValueFromViewPro(_ match{ case v: LT => v.Bid.qntyPro;   case v => Pro.O.constant(v.Bid.qnty) })}
  class BidPriceColumn    extends Column[Price] {         Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.Bid.pricePro;  case v => Pro.O.constant(v.Bid.price) })}
  class AskLotsColumn     extends Column[Qnty] {            Ui.LotConfig(this); useValueFromViewPro(_ match{ case v: LT => v.Ask.qntyPro;   case v => Pro.O.constant(v.Ask.qnty) })}
  class AskPriceColumn    extends Column[Price] {         Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.Ask.pricePro;  case v => Pro.O.constant(v.Ask.price) })}
  class LastPriceColumn   extends Column[Price]("Last"){  Ui.PriceConfig(this); useValueFromViewPro(_ match{ case v: LT => v.Last.pricePro; case v => Pro.O.constant(v.Last.price) })}
  class PriceChangeColumn extends Column[Percent] { Ui.PriceChangeConfig(this); useValueFromViewPro(_ match{ case v: LT => v.changePro;     case v => Pro.O.constant(v.change) })}

  class SpreadPercentColumn extends Column[Number]("%", 25):
    useValueFromViewPro(_ match{ case v: LT => v.spreadPro.mapView(_.toNumber); case v => Pro.O.constant(v.spread).mapView(_.toNumber) })
    useTooltip(v => "Bid/Ask Spread Percent")
    Ui.IndexConfig(this);
    useFormat(Table.Format(_))


object Table:
   val Format = Math.Format("#.#")

