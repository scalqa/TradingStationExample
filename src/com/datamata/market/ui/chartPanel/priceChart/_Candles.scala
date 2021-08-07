package com.datamata; package market; package ui; package chartPanel; package priceChart; import language.implicitConversions

import scala.language.reflectiveCalls

transparent trait _Candles:
  self: Ui.ChartPanel.PriceChart =>

  object Candles extends Series:
    def apply(l: Idx[Quote.Bar]): Unit =
      items = Idx.O.wrap(l).statefulMap_^(s => {
        class CandleItem extends Item((s.start + s.duration).genTime, s.close) {
          override lazy  val node : Z.CandleNode = new Z.CandleNode
          layoutJob = () => {
            node.update(s, axisY.apply, 6);
            node.layoutX = axisX(x) max 2;
            node.layoutY = axisY(s.open)
          }
          override def doc = super.doc += ("high", node.tooltip.high.text) += ("low", node.tooltip.low.text)
        }
        new CandleItem()
      })
