package com.datamata; package market; package ui; package base; package Quote; package Bar; import language.implicitConversions

trait Table[RAW] extends Ui.Table[RAW]:
  type VIEW <: M.Quote.Bar

  protected def setupDefaultColumns: Unit = { TimeColumn();  DurationColumn(); VolumeColumn(); OHLCColumn() }

  class VolumeColumn   extends Column[Qnty] { Ui.VolumeConfig(this); valueView_:(_.volume) }
  class TimeColumn     extends Column[Time] { Ui.TimeConfig(this); valueView_:(_.start) }
  class DurationColumn extends Column[Duration]("Dur", 35) { valueView_:(_.duration) }

  class OHLCColumn extends Column[String]("O-H-L-C", 125):
    styleClass = Ui.MktPriceClass
    //alignment = Fx.LEFT
    valueView_:(b => {
      val f = Math.Format("###.00#")
      if (b.isFlat) f(b.close)
      else f(b.open) + "  " + f(b.high) + "  " + f(b.low) + "  " + f(b.close)
    })



