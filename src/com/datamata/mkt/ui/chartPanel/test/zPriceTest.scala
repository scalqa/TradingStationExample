package com.datamata; package mkt; package ui; package chartPanel; package test; import language.implicitConversions

object zPriceTest extends Fx.Application(900, 500, "CandleStick Test"):

  object View extends Ui.ChartPanel.PriceChart:


    //    //qr = Quote.Range.A.Sample.Small
    //    //qr = Quote.Range.A.Sample.Medium
    //    qr = Quote.Range.A.Sample.Large
    Candles(SampleQuoteData)
    LastQuotes(SampleQuoteData.map_^(s => Quote(s.volume, s.close, s.start)))
