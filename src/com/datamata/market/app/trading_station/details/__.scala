package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

import details.*

class Details extends Ui.Module.Detail[Position]:
  self =>

  protected object View extends Fx.Pane.Tab:
    add("",       new ui.base.ticker.detail.Stats().^(_.bindTo(self)))
    add(ExecutionsTab(self))
    add("Quotes", new ui.base.ticker.detail.Quotes().^(_.bindTo(self)))
    add("Data",   new ui.base.ticker.detail.Data(true).^(_.bindTo(self)).^(_.View.selection.selectAt(1)))
    add(ChartTab(self));
    selection.selectAt(2)

  self.onValueChange(p => View.tabs.head.text = p.symbol.ticker.tag)

object Details extends Details:

  Positions.selection.onChange(s =>
    Fx.Thread.scheduleIn(1.Millis, Details() = s.value_?.map(_.position) or \/)
  )
