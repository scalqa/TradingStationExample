package com.datamata; package market; package ui; package chartPanel; package priceChart; import language.implicitConversions

class PriceChart(timeIdx: Time.Line = VOID) extends Fx.Chart.XY.X.Basic[Gen.Time, Price, TimeAxis, PriceAxis](new TimeAxis, new PriceAxis) with _Candles with _Quotes with _TradeFills with _Orders:
  type ITEM = Item
  type SERIES = Series

  data += Candles += AskQuotes += LastQuotes += BidQuotes += TradeFills += Orders
