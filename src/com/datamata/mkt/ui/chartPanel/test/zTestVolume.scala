package com.datamata; package mkt; package ui; package chartPanel; package test; import language.implicitConversions

object zTestVolume extends Fx.Application(900, 500, "Volume Test"):

  object View extends VolumeChart:
    Bars(SampleQuoteData)

