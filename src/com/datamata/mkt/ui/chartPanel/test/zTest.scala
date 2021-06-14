package com.datamata; package mkt; package ui; package chartPanel; package test; import language.implicitConversions

object zTest extends Fx.Application(900, 500, "Chart Test"):

  object View extends ChartPanel(Time.Line.Trading):
    apply(Idx.O.wrap(SampleQuoteData))
