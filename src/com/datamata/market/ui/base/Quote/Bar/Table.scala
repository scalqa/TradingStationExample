package com.datamata; package market; package ui; package base; package quote; package bar; import language.implicitConversions

trait Table[RAW] extends Ui.Table[RAW]:
  type VIEW <: M.Quote.Bar

  protected def setupDefaultColumns: Unit = { TimeColumn();  DurationColumn(); VolumeColumn(); OHLCColumn() }

  class VolumeColumn   extends Column[Qnty] { Ui.VolumeConfig(this);  useValueFromView(_.volume) }
  class TimeColumn     extends Column[Time] { Ui.TimeConfig(this);  useValueFromView(_.start) }
  class DurationColumn extends Column[Duration]("Dur", 35) {  useValueFromView(_.duration) }

  class OHLCColumn extends Column[String]("O-H-L-C", 125):
    styleClass = Ui.MktPriceClass
    //alignment = Fx.LEFT
    useValueFromView(b => {
      val f = Math.Format("###.00#")
      if (b.isFlat) f(b.close)
      else f(b.open) + "  " + f(b.high) + "  " + f(b.low) + "  " + f(b.close)
    })



