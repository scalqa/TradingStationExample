package com.datamata; package market; package ui; package base; package ticker; package detail; import language.implicitConversions

class Data(val reversed: Boolean = false) extends Ui.Module.Detail[M.Ticker]:
  val trade = new Listing(LAST)
  val bid   = new Listing(BID)
  val ask   = new Listing(ASK)

  object View extends Fx.Pane.Tab:
    add("bid", bid);
    add("trade", trade);
    add("ask", ask);

  protected def tabPane: Fx.Pane.Tab = Fx.Abstract.Node.FxConverter(real).asInstanceOf[Fx.Pane.Tab]

  // *********************************************************************************************************
  class Listing(val typ: M.Quote.Type) extends Ui.Module.Listing[M.Quote.Bar] {
    val Format = Gen.Math.Format("##.00")

    protected object Table extends quote.bar.Table[M.Quote.Bar] {
      setupDefaultColumns

      val indexs = (0 <> 3).stream.map(new Index(_)).pack

      class Index(i: Int) extends Column[Double]("#", 60) {
        useValueOpt(_.inds.applyOpt(i));
        useFormat(Format.apply(_), _ => "")
      }
    }

    Data.this.asInstanceOf[Pro.OM[M.Ticker]].onValueChange(t =>
      t.??
      .map(_.quote(typ).range)
      .forval(r => {
          Table.items = Idx.O.wrap(r).reversedView
          val l = r.indexStream.map(_.name).pack
          Table.indexs.stream.foreachIndexed((i, v) => v.label = l.applyOpt(i) or "#")
        })
      .fornil {
        Table.items = VOID
        Table.indexs.stream.foreach(_.label = "#")
      })
  }

