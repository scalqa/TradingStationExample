package com.datamata; package market; package ui; package context; import language.implicitConversions

object Config:

  export StyleClass.*

  object Price extends ColumnConfig[Price]("Price", c => {
    c.styleClass = MktPriceClass;
    c.useFormat(_.format("###.00#"), _ => "")
  })

  object Pl extends ColumnConfig[Amount]("PL", c => {
    c.styleClass = MktPlClass
    c.useFormat(_.toLong.abs.tagBrief, _ => "")
    c.useCellSetup(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.valueOpt.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object Pl_% extends ColumnConfig[Percent]("Pl%", c => {
    c.styleClass = MktPlClass;
    c.useFormat(p => Context.MathFormat(p.abs) + "%")
    c.useCellSetup(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.valueOpt.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object Amount extends ColumnConfig[Amount]("Amount", c => { c.styleClass = MktAmountClass; c.useFormat(_.format("###.##")) })

  object BriefAmount extends ColumnConfig[Amount]("Amount", c => { c.styleClass = MktAmountClass; c.useFormat(_.tagBrief) })

  object Volume extends ColumnConfig[Qnty]("Vol", c => { c.styleClass = MktLotsClass; c.useFormat(_.tagBrief) })

  object Lot extends ColumnConfig[Qnty]("Lots", c => { c.styleClass = MktLotsClass; c.useFormat(_.lots.abs.format("##.##")) })

  object Qnty extends ColumnConfig[Qnty]("Lots", c => {
    c.styleClass = MktLotsClass;
    c.useFormat(_.lots.format("##.##"), _ => "")
    c.useCellSetup(l => {
      val ll = l.cast[Fx.Table.Cell[_,_,Percent]]
      ll.valueOpt.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0))
    })
  })

  object PriceChange extends ColumnConfig[Percent]("%", c => {
    c.styleClass = MktPriceChangeClass
    c.useFormat(p => Context.MathFormat.apply(p.abs))
    c.useCellSetup(l => l.cast[Fx.Table.Cell[_,_,Percent]].valueOpt.forval(v => Context.PositiveNegativePseudoGroup(l, v >= 0)))
  })

  object Index extends ColumnConfig[Number]("", 30, c => { c.styleClass = MktIndexClass; c.useFormat(n => if(n.doubleValue==0) "" else Context.MathFormat.apply(n)) })

  object Symbol extends ColumnConfig[Symbol]("Symbol", c => { c.styleClass = MktSymbolClass; c.useFormat(_.ticker); c.alignment = Fx.Pos.BaselineLeft })

  object Time extends ColumnConfig[Time]("Time", 120, c => {
    c.styleClass = MktTime
    c.useFormat {
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
    c. useProUpgrade((_, p) => if (p().take(_.age >= 5.Minute)) p else { p.observableView(Gen.Time.currentPro(1.Second)) })
  })

