package com.datamata; package market; package ui; package chartPanel; import language.implicitConversions

class VolumeAxis extends Fx.Chart.Axis.X.Custom[Qnty]("Volume", TwoWayFunction(v => v.toDouble, d => Qnty(d.toLong))):

  setup.forceZeroInRange = true


