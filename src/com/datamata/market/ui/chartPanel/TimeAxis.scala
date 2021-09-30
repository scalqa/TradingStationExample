package com.datamata; package market; package ui; package chartPanel; import language.implicitConversions

class TimeAxis private (val timeline_* :TimeAxis.TimelineProperty) extends Fx.Chart.Axis.X.Time("Time", timeline_*):
  def this(tl: Time.Line = Time.Line.Void) = this(new TimeAxis.TimelineProperty(tl))

  def timeline = timeline_*()
  def timeline_=(tl: Time.Line) = timeline_*() = tl

object TimeAxis:

  class TimelineProperty(tl: Time.Line) extends Pro.M.X.Basic[Time.Line](tl) with TwoWayFunction[Gen.Time, Double]:

    def apply(t: Gen.Time): Double = tl.millisTotal(t.mktTime).toDouble

    def undo(d: Double): Gen.Time = tl.time(d.toLong).genTime

