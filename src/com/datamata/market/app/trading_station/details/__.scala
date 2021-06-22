package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

import details.*

class Details extends Ui.Module.Detail[Position]:
  self =>

  protected object View extends Fx.Pane.Tab:
    add("Doc",   new ui.base.Ticker.Detail.Stats().^(_.bindTo(self)))
    add(ExecutionsTab(self))
    add("Quotes", new ui.base.Ticker.Detail.Quotes().^(_.bindTo(self)))
    add("Data",   new ui.base.Ticker.Detail.Data(true).^(_.bindTo(self)).^(_.View.selection.set(1)))
    add(ChartTab(self));
    selection.set(2)

  self.onValueChange(p => View.tabs.head.text = p.symbol.ticker)

object Details extends Details:

  Positions.selection.onChange(s =>
    Fx.Thread.scheduleIn(1.Millis, Details() = s.get_?.map(_.position) or \/)
  )
