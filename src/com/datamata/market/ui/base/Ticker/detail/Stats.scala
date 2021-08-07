package com.datamata; package market; package ui; package base; package ticker; package detail; import language.implicitConversions

class Stats extends Ui.Module.Detail[Ticker]:
  val symbol, close, high, low, volume, average =  Fx.Label()

  this.asInstanceOf[Pro.OM[Ticker]].onValueChange(t => {
    symbol.text = t.symbol.toString
    close.text = t.close.toString
    high.text = t.high.toString
    low.text = t.low.toString
    volume.text = t.volume.toBrief
    average.text = {
      val v = t.averageDay
      Percent(t.volume.real * t.close.real, v.real).toInt.toString + "% / " + v.toBrief
    }
  })

  protected object View extends Fx.Pane.Grid:
    padding = Fx.Insets(10)
    add(0, 0, "Symbol: ", symbol)
    add(1, 0, "Close: ", close)
    add(2, 0, "High: ", high)
    add(3, 0, "Low: ", low)
    add(4, 0, "Volume: ", volume)
    add(5, 0, "Amount: ", average)
    add(6, 0, "")
    add(7, 0, new Ui.MemoryPanel)
    // add(2, 0, new Fx.Button("Detach",  {val dt = new Fx.Stage("Details", 500, 300, new __(); dt() = pro()).show}))
