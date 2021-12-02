package com.datamata; package market; package ui; package base; package tape; import language.implicitConversions

class Detail(t: Live.Tape = VOID) extends Ui.Module.Detail[Live.Tape](t):

  object Field:
    val size, time =  Fx.Label()
    val name = Fx.Label().self(_.textPro.bindTo(Detail.this.mapView(_.name)))
    val filter = Fx.Text.Field("").self(_.onActionRun(TickerBlock.replaceTickers))

  Fx.Thread.scheduleEvery(1.Second, {
    Field.size.text = this().Tickers.size.toString
    Field.time.text = this().time.dayTime.roundTo(1.Second)(using DOWN).tag
  })

  protected object View extends Fx.Pane.Split:
    orientation = VERTICAL
    add(new Fx.Pane.Grid {
      add(0, 0, " Tape:", Field.name)
      add(0, 2, Fx.Button("Menu", Fx.Thread(Fx.Popup.Menu(Detail.this().actionStream).show(scene.window))))
      add(0, 3, "    Size:", Field.size);
      add(0, 5, "    Time:", Field.time);
      add(0, 7, "    Filter:", Field.filter)
      add(0, 9,  Fx.Button("Search", TickerBlock.replaceTickers))
      add(0, 10, Fx.Button("Clear",  TickerBlock.Table.rows.replaceWith(VOID)))
    }, 1.Percent)
    add(TickerBlock, 40.Percent)
    add(TickerDetail)

  object TickerBlock extends Ui.Module.Listing[Live.Ticker]:
    object Table extends ticker.Table[Live.Ticker]{ setupDefaultColumns }
    def replaceTickers: Unit = Table.rows.replaceWith(Detail.this().?.map(_.Tickers.stream.take(_.symbol.toString.startsWith(Field.filter.text)).takeFirst(100)) or VOID)

  object TickerDetail extends base.ticker.Detail[Live.Ticker]:
    this.bindTo[Live.Ticker](TickerBlock.selection, VOID)

