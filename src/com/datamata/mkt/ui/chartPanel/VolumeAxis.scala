package com.datamata; package mkt; package ui; package chartPanel; import language.implicitConversions

class VolumeAxis extends Fx.Chart.Axis.X.Custom[Qnty]("Volume", ReversibleFunction(v => v.Double, d => Qnty(d.Long))):

  setup.forceZeroInRange = true


