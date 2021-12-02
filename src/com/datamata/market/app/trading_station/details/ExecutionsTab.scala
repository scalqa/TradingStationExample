package com.datamata.market; package app.trading_station; package details; import Live.*; import language.implicitConversions

class ExecutionsTab(pro: Pro.O[Position]) extends Fx.Pane.Tab.Panel("Executions"):
  ({
    content = new Fx.Pane.Split(HORIZONTAL) {
      this.add(Executions, 75.Percent)
      this.add(Orders)
    }
  })

  class Row(val real: Execution):
    val totalQntyPro = Pro.OM[Qnty](VOID)

  // *************************************************************************
  object Executions extends Ui.Module.Listing[Row]:
    pro.onValueChange(p => {
      Table.items = p.fills.reversedView.statefulMapView(new Row(_)).self(_.onAdd(_ => recalculate))
      recalculate
    })

    object Table extends ui.base.trade.Fill.Table[Row]:
      type VIEW = Execution
      useView(_.real)
      setupDefaultColumns
      new Column[Number]  ("A")         {  useValueFromView(_.amount.index); IndexConfig(this) }.reposition(2)
      new Column[Qnty]    ("Total", 33) { useValuePro(_.totalQntyPro); LotConfig(this) }
      new Column[Order.Id]("Order", 55) {  useValueFromView(_.orderId) }
      sortMode = Fx.Table.SortMode.ProxyWithUnsorted

    private def recalculate =
      Table.items.stream.reverse.zipFoldAs(VOID :Qnty, _ + _.real.qnty).foreach(t => t._1.totalQntyPro() = t._2)
      Orders.Table.items = pro().fills.stream.sortBy(_.orderId).reverse.groupWith(_.orderId).map(t => {
        val (q, a, f) = t._2.sumFew(_.qnty, _.amount, _.fee)
        (t._1, q.toLong.Qnty, a, f)
      }).pack

  // *************************************************************************
  object Orders extends Ui.Module.Listing[(Order.Id, Qnty, Amount, Amount)]:
    object Table extends Ui.Table[(Order.Id, Qnty, Amount, Amount)]:
      new Column[Order.Id]("Order", 55) { useValue(_._1) }
      new Column[Qnty]    ("Lots")      { useValue(_._2); QntyConfig(this) }
      new Column[Number]  ("A")         { useValue(_._3.index); IndexConfig(this) }
      new Column[Amount]  ("Fee")       { useValue(_._4); AmountConfig(this) }
