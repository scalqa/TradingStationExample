package com.datamata; package mkt; package ui; package chartPanel; import language.implicitConversions

transparent trait _properties:
  self: ChartPanel =>

  lazy  val showVolume_* = Boolean.Pro.OM (false).^(p => {
    p.onValueChange(b => {
      if (b) {
        center = splitPane
        splitPane.add(priceChart, 80.Percent).add(volumeChart)
      } else {
        splitPane.items.clear
        center = priceChart
      }
      priceChart.axisX.ticksVisible = !b
    })
    p() = true
  })

  /**/  def showVolume     = showVolume_*();  def showVolume_=(v: Boolean) = showVolume_*() = v
  lazy  val showTrades_*   = Boolean.Pro.OM (false).^(_.onValueChange(priceChart.LastQuotes.show_*()= _))
  lazy  val showBidsAsks_* = Boolean.Pro.OM (false).^(_.onValueChange(v => { priceChart.BidQuotes.show_*() = v; priceChart.AskQuotes.show_*() = v }))
