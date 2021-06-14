package com.datamata; package mkt; package ui; package chartPanel; import language.implicitConversions

class VolumeChart(timeIdx: Time.Line = \/) extends Fx.Chart.XY.X.Basic[Gen.Time, Qnty, TimeAxis, VolumeAxis](new TimeAxis(timeIdx), new VolumeAxis):
  type ITEM = Bar
  type SERIES = Series

  data += Bars

  object Bars extends Series:
    def apply(l: Idx[Quote.Bar]): Unit = items = Idx.O.wrap(l).statefulMap_^(new Bar(_))

  class Bar(s: Quote.Bar) extends Item((s.start + s.duration).general, s.volume) with Observable.X.Basis:
    override def fireAnyChange = { y = s.volume; 1 }
    override lazy  val node : Fx.Shape.Line = Fx.Shape.Line()
    layoutJob = () =>
      node.startY = axisY.height
      node.endY   = axisY(y)
      node.startX = axisX(x)
      node.endX   = axisX(x)


