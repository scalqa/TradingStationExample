package com.datamata; package market; package ui; import chartPanel.*; import language.implicitConversions

class ChartPanel(val timeIdx: Time.Line = \/) extends Fx.Pane.Border with _properties:

  val splitPane = Fx.Pane.Split(VERTICAL)

  val priceChart = new Ui.ChartPanel.PriceChart(timeIdx).^(cs => {
    cs.axisX.ticksVisible = false
    cs.axisX.label = \/
    cs.axisY.label = \/
    padding = padding.newBottom(0)
  })

  val volumeChart = new VolumeChart(timeIdx).^(v => {
    prefHeight = 50
    padding = padding.newTop(0)
    v.axisX.label = \/
    v.axisY.label = \/
  })

  def apply(spreads: Idx.O[Quote.Bar], quotes: Idx.O[Quote] = \/, trades: Idx.O[Trade.Fill] = \/): Unit =
    priceChart.Candles(spreads)
    priceChart.LastQuotes(quotes)
    priceChart.TradeFills(trades)
    volumeChart.Bars(spreads)

  def apply(p: Live.Position): Unit =
    priceChart.Candles(p.Last.range)
    priceChart.AskQuotes(p.ticker.Ask.list)
    priceChart.LastQuotes(p.ticker.Last.list)
    priceChart.BidQuotes(p.ticker.Bid.list)
    priceChart.TradeFills(p.fills)
    volumeChart.Bars(p.Last.range)

  def apply(b: Quote.Range): Unit =
    priceChart.Candles(b)
    volumeChart.Bars(b)

object ChartPanel:
  type PriceChart = priceChart.PriceChart
