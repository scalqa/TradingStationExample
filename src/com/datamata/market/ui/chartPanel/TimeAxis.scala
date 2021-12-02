package com.datamata; package market; package ui; package chartPanel; import language.implicitConversions

class TimeAxis private (val timelinePro :TimeAxis.TimelineProperty) extends Fx.Chart.Axis.X.Time("Time", timelinePro):
  def this(tl: Time.Line = Time.Line.Void) = this(new TimeAxis.TimelineProperty(tl))

  def timeline = timelinePro()
  def timeline_=(tl: Time.Line) = timelinePro() = tl

object TimeAxis:

  class TimelineProperty(tl: Time.Line) extends Pro.M.X.Basic[Time.Line](tl) with TwoWayFunction[Gen.Time, Double]:

    def apply(t: Gen.Time): Double = tl.millisTotal(t.mktTime).toDouble

    def undo(d: Double): Gen.Time = tl.time(d.toLong).genTime

