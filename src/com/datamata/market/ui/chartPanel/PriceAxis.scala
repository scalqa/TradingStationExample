package com.datamata; package market; package ui; package chartPanel; import language.implicitConversions

class PriceAxis extends Fx.Chart.Axis.X.Numbers[Price]("Price", TwoWayFunction(_.real, Price(_))):

  //    tickUnit = 1800000;
  minorTickCount = 30;
  forceZeroInRange = false
  //onFormat(p => p.format("#0.00##"))

  //    override def calcRange(from: Price, as: Price, length: Double, labelSize: Double): Range = {
  //      var r = super.calcRange(from, as, length, labelSize)
  //      if (r.tickUnit % 0.01 > 0) r.newFrom(r.tickUnit - r.tickUnit % 0.01)
  //      r
  //    }
