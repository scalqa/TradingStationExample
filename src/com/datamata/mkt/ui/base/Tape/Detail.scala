package com.datamata; package mkt; package ui; package base; package Tape; import language.implicitConversions

class Detail(t: M.Live.Tape = \/) extends Ui.Module.Detail[M.Live.Tape](t):

  object Field:
    val size, time =  Fx.Label()
    val name = Fx.Label().^(_.text_*.bindTo(Detail.this.map_^(_.name)))
    val filter = Fx.Text.Field("").^(_.onActionRun(TickerBlock.replaceTickers))

  Fx.Thread.scheduleEvery(1.Second, {
    Field.size.text = this().Tickers.size.toString
    Field.time.text = this().time.dayTime.roundTo(1.Second)(using DOWN).tag
  })

  protected object View extends Fx.Pane.Split:
    orientation = VERTICAL
    add(new Fx.Pane.Grid {
      add(0, 0, " Tape:", Field.name)
      add(0, 2, Fx.Button("Menu", Fx.Thread(Fx.Popup.Menu(Detail.this().action_~).show(scene.window))))
      add(0, 3, "    Size:", Field.size);
      add(0, 5, "    Time:", Field.time);
      add(0, 7, "    Gen.Filter:", Field.filter)
      add(0, 9, Fx.Button("Refresh", TickerBlock.replaceTickers))
      add(0, 10, Fx.Button("Clear", TickerBlock.Table.rows.replaceAll(\/)))
    }, 1.Percent)
    add(TickerBlock, 40.Percent)
    add(TickerDetail)

  object TickerBlock extends Ui.Module.Listing[M.Live.Ticker]:
    object Table extends Ticker.Table[M.Live.Ticker] { setupDefaultColumns }
    def replaceTickers: Unit = Table.rows.replaceAll(Detail.this().?.map(_.Tickers.~.take(_.symbol.toString.startsWith(Field.filter.text)).takeFirst(100)) or \/)

  object TickerDetail extends base.Ticker.Detail[M.Live.Ticker]:
    this.bindTo[M.Live.Ticker](TickerBlock.selection, \/)

