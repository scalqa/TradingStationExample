package com.datamata; package market; package ui; package chartPanel; import language.implicitConversions

transparent trait _properties:
  self: ChartPanel =>

  lazy  val showVolumePro = Boolean.Pro.OM (false).self(p => {
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

  /**/  def showVolume     = showVolumePro();  def showVolume_=(v: Boolean) = showVolumePro() = v
  lazy  val showTradesPro   = Boolean.Pro.OM (false).self(_.onValueChange(priceChart.LastQuotes.showPro()= _))
  lazy  val showBidsAsksPro = Boolean.Pro.OM (false).self(_.onValueChange(v => { priceChart.BidQuotes.showPro() = v; priceChart.AskQuotes.showPro() = v }))
