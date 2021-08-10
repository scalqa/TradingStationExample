package com.datamata; package market; package ui; package context; import language.implicitConversions

object Config:

  export StyleClass.*

  object Price extends ColumnConfig[Price]("Price", c => {
    c.styleClass = MktPriceClass;
    c.format_:(_.format("###.00#"), _ => "")
  })

  object Pl extends ColumnConfig[Amount]("PL", c => {
    c.styleClass = MktPlClass
    c.format_:(_.toLong.abs.tagBrief, _ => "")
    c.cell_:(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.value_?.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object Pl_% extends ColumnConfig[Percent]("Pl%", c => {
    c.styleClass = MktPlClass;
    c.format_:(p => Context.MathFormat(p.abs) + "%")
    c.cell_:(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.value_?.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object Amount extends ColumnConfig[Amount]("Amount", c => { c.styleClass = MktAmountClass; c.format_:(_.format("###.##")) })

  object BriefAmount extends ColumnConfig[Amount]("Amount", c => { c.styleClass = MktAmountClass; c.format_:(_.tagBrief) })

  object Volume extends ColumnConfig[Qnty]("Vol", c => { c.styleClass = MktLotsClass; c.format_:(_.tagBrief) })

  object Lot extends ColumnConfig[Qnty]("Lots", c => { c.styleClass = MktLotsClass; c.format_:(_.lots.abs.format("##.##")) })

  object Qnty extends ColumnConfig[Qnty]("Lots", c => {
    c.styleClass = MktLotsClass;
    c.format_:(_.lots.format("##.##"), _ => "")
    c.cell_:(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.value_?.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object PriceChange extends ColumnConfig[Percent]("%", c => {
    c.styleClass = MktPriceChangeClass
    c.format_:(p => Context.MathFormat.apply(p.abs))
    c.cell_:(l => l.cast[Fx.Table.Cell[_,_,Percent]].value_?.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0)))
  })

  object Index extends ColumnConfig[Number]("", 30, c => { c.styleClass = MktIndexClass; c.format_:(n => if(n.doubleValue==0) "" else Context.MathFormat.apply(n)) })

  object Symbol extends ColumnConfig[Symbol]("Symbol", c => { c.styleClass = MktSymbolClass; c.format_:(_.ticker); c.alignment = Fx.Pos.BaselineLeft })

  object Time extends ColumnConfig[Time]("Time", 120, c => {
    c.styleClass = MktTime
    c.format_: {
      case t if (t.isVoid || t == M.Time.min) => ""
      case t if (t.day.isCurrent) =>
        //        val ct = Time.current
        //        val len = (t lengthTo ct).roundTo(5.Seconds, Core.Rounding.Down)
        //        if (len < 5.Minute) if (ct < t) "in " + len else len.tagBrief
        //        else
        t.dayTime.tagBrief
      case t =>
        val s = t.roundTo(1.Second)(using DOWN).tag
        if (t.year.isCurrent) s.dropFirst(5) else s
      //        val s = t.roundTo(1.Second)(using DOWN).toString
      //        if (t.year.isCurrent) s.dropFirst(5) else s
    }
    c.enhance_:*?((_, p) => if (p().take(_.age >= 5.Minute)) p else { p.observable_^(Gen.Time.current_*(1.Second)) })
  })

