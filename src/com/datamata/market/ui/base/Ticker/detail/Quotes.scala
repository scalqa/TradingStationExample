package com.datamata; package market; package ui; package base; package ticker; package detail; import language.implicitConversions

class Quotes extends Ui.Module.Detail[M.Ticker]:
  val ask   = new Block(ASK)
  val bid   = new Block(BID)
  val trade = new Block(LAST)

  protected lazy  val View = Fx.Pane.Split(HORIZONTAL).self(_ add (bid, 33.Percent) add (trade, 33.Percent) add (ask, 33.Percent))

  // *****************************************************************************************
  class Block(val typ: M.Quote.Type) extends Ui.Module.Listing[M.Quote] :

    Quotes.this.onChangeRun { Table.items = Quotes.this().??.map(v => Idx.O.wrap(v.quote(typ).list).reversedView) or VOID }

    protected object Table extends quote.Table[Quote] :
      setupDefaultColumns
      columns.stream.takeType[QntyColumn].readOpt.forval(c => {
        c.label = typ.toString.replaceAll("Last", "Trade") + 's'
        if (typ.isAsk) c.useValueFromView(_.qnty.abs)
      })
      sortMode = Fx.Table.SortMode.ProxyWithUnsorted


