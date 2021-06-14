package com.datamata; package mkt; package ui; package chartPanel; package priceChart; import language.implicitConversions

transparent trait _Orders:
  self: Ui.ChartPanel.PriceChart =>

  object Orders extends Series:
    var cnt = 0
    def apply(l: Idx[Order]): Unit =
      items = Idx.O.wrap(l).statefulMap_^(f => new Item(f.created.general, f.price) {
        val hash = { cnt += 1; cnt }
        override lazy  val node : Fx.Shape.Circle = new Fx.Shape.Circle(0) {
          new Fx.Tooltip {
            graphic = new Fx.Pane.Grid {
              add(0, 0, "Ordered", f.qnty.toString);
              add(1, 0, "for", f.price.toString);
              add(2, 0, "at", f.created.toString)
            }
          }.attachTo(this)
        }
        layoutJob = () => {
          node.centerY = axisY(y);
          node.centerX = axisX(x);
          node.radius = { val a = f.amount.abs; if (a < 1000) 2 else if (a < 6000) 3 else if (a < 10000) 4 else 5 }
          node.fill = Fx.Color.Green
        }
      })
