package com.datamata; package market; package ui; package base; package ticker; import language.implicitConversions

import  Live.{ Ticker => LT }

trait Table[RAW] extends Ui.Table[RAW]:
  type VIEW <: M.Ticker

  protected def setupDefaultColumns: Unit = setupTickerColumns;

  private[market] def setupTickerColumns: Unit =
    SymbolColumn(); OpenColumn(); HighColumn(); LowColumn(); VolumeColumn(); BidLotsColumn(); BidPriceColumn();
    SpreadPercentColumn(); AskPriceColumn(); AskLotsColumn(); LastPriceColumn(); PriceChangeColumn()

  class SymbolColumn      extends Column[Symbol] {       Ui.SymbolConfig(this); valueView_:(_.symbol) }
  class OpenColumn        extends Column[Price]("Open") { Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.open_*;       case v => Pro.O.constant(v.open) })}
  class HighColumn        extends Column[Price]("High") { Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.high_*;       case v => Pro.O.constant(v.high) })}
  class LowColumn         extends Column[Price]("Low")  { Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.low_*;        case v => Pro.O.constant(v.low) })}
  class CloseColumn       extends Column[Price]("Close") {Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.close_*;      case v => Pro.O.constant(v.close) })}
  class VolumeColumn      extends Column[Qnty] {         Ui.VolumeConfig(this); valueView_:*(_ match{ case v: LT => v.volume_*;     case v => Pro.O.constant(v.volume) })}
  class BidLotsColumn     extends Column[Qnty] {            Ui.LotConfig(this); valueView_:*(_ match{ case v: LT => v.Bid.qnty_*;   case v => Pro.O.constant(v.Bid.qnty) })}
  class BidPriceColumn    extends Column[Price] {         Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.Bid.price_*;  case v => Pro.O.constant(v.Bid.price) })}
  class AskLotsColumn     extends Column[Qnty] {            Ui.LotConfig(this); valueView_:*(_ match{ case v: LT => v.Ask.qnty_*;   case v => Pro.O.constant(v.Ask.qnty) })}
  class AskPriceColumn    extends Column[Price] {         Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.Ask.price_*;  case v => Pro.O.constant(v.Ask.price) })}
  class LastPriceColumn   extends Column[Price]("Last"){  Ui.PriceConfig(this); valueView_:*(_ match{ case v: LT => v.Last.price_*; case v => Pro.O.constant(v.Last.price) })}
  class PriceChangeColumn extends Column[Percent] { Ui.PriceChangeConfig(this); valueView_:*(_ match{ case v: LT => v.change_*;     case v => Pro.O.constant(v.change) })}

  class SpreadPercentColumn extends Column[Number]("%", 25):
    valueView_:*(_ match{ case v: LT => v.spread_*.map_^(_.Number); case v => Pro.O.constant(v.spread).map_^(_.Number) })
    tooltip_:(v => "Bid/Ask Spread Percent")
    Ui.IndexConfig(this);
    format_:(Table.Format(_))


object Table:
   val Format = Math.Format("#.#")

