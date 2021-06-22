package com.datamata; package market; package ui; package base; package Ticker; package detail; import language.implicitConversions

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

    protected object Table extends Quote.Bar.Table[M.Quote.Bar] {
      setupDefaultColumns

      val indexs = (0 <> 3).~.map(new Index(_)).><

      class Index(i: Int) extends Column[Double]("#", 60) {
        value_:?(_.inds.at_?(i));
        format_:(Format.apply(_), _ => "")
      }
    }

    Data.this.asInstanceOf[Pro.OM[M.Ticker]].onValueChange(t =>
      t.^.?
      .map(_.quote(typ).range)
      .forval(r => {
          Table.items = Idx.O.wrap(r).reversed_^
          val l = r.index_~.map(_.name).><
          Table.indexs.~.foreachIndexed((i, v) => v.label = l.at_?(i) or "#")
        })
      .fornil {
        Table.items = \/
        Table.indexs.~.foreach(_.label = "#")
      })
  }

