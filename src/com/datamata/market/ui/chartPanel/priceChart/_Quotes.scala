package com.datamata; package market; package ui; package chartPanel; package priceChart; import language.implicitConversions

transparent trait _Quotes:
  self: Ui.ChartPanel.PriceChart =>

  class Quotes extends Series:
    var i: Idx[Quote] = VOID
    val showPro = Boolean.Pro.OM (false).self(_.onChangeRun(apply(i)))

    def apply(l: Idx[Quote]): Unit =
      i = l
      items = if (!showPro()) VOID else Idx.O.wrap(l).statefulMapView(q => new Item(q.created.genTime, q.price) {
        override lazy  val node : Fx.Shape.Path = Fx.Shape.Path().self(_.stroke = Fx.Color.LightBlue)
        layoutJob = () => node.elements.replaceWith(items.stream.zipIndex.map((i,it) => (if (i == 0) Fx.Shape.Path.MoveTo(it.xPos, it.yPos) else Fx.Shape.Path.LineTo(it.xPos, it.yPos)) :Fx.Shape.Path.Element))
      })

  object AskQuotes  extends Quotes
  object LastQuotes extends Quotes
  object BidQuotes  extends Quotes


