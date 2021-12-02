package com.datamata; package market; package ui; package chartPanel; package priceChart; import language.implicitConversions

transparent trait _TradeFills:
  self: Ui.ChartPanel.PriceChart =>

  object TradeFills extends Series:
    var cnt = 0

    def apply(l: Idx[Trade.Fill]): Unit =
      items = Idx.O.wrap(l).statefulMapView(f => new Item(f.created.genTime, f.price) {
        val hash = { cnt += 1; cnt }
        override lazy  val node : Fx.Shape.Circle = new Fx.Shape.Circle(0) {
          new Fx.Tooltip {
            graphic = new Fx.Pane.Grid {
              add(0, 0, if (f.qnty < 0) "Sold " else "Bought  ", f.qnty.abs.toString);
              add(1, 0, "for", f.price.toString);
              add(2, 0, "at", f.created.toString)
            }
          }.attachTo(this)
        }
        layoutJob = () => {
          node.centerY = axisY(y);
          node.centerX = axisX(x);
          node.radius = { val a = f.amount.abs; if (a < 1000) 2 else if (a < 6000) 3 else if (a < 10000) 4 else 5 }
          node.fill = if (f.qnty < 0) Fx.Color(97, 0, 67) else Fx.Color(0, 0, 150)
        }
      })
