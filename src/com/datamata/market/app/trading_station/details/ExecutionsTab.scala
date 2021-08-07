package com.datamata.market; package app.trading_station; package details; import Live.*; import language.implicitConversions

class ExecutionsTab(pro: Pro.O[Position]) extends Fx.Pane.Tab.Panel("Executions"):
  ({
    content = new Fx.Pane.Split(HORIZONTAL) {
      this.add(Executions, 75.Percent)
      this.add(Orders)
    }
  })

  class Row(val real: Execution):
    val totalQnty_* = Pro.OM[Qnty](\/)

  // *************************************************************************
  object Executions extends Ui.Module.Listing[Row]:
    pro.onValueChange(p => {
      Table.items = p.fills.reversed_^.statefulMap_^(new Row(_)).^(_.onAdd(_ => recalculate))
      recalculate
    })

    object Table extends ui.base.trade.Fill.Table[Row]:
      type VIEW = Execution
      view_:(_.real)
      setupDefaultColumns
      new Column[Number]  ("A")         { valueView_:(_.amount.index); IndexConfig(this) }.reposition(2)
      new Column[Qnty]    ("Total", 33) { value_:*(_.totalQnty_*); LotConfig(this) }
      new Column[Order.Id]("Order", 55) { valueView_:(_.orderId) }
      sortMode = Fx.Table.SortMode.ProxyWithUnsorted

    private def recalculate =
      Table.items.~.reverse.zipFoldAs(\/ :Qnty, _ + _.real.qnty).foreach(t => t._1.totalQnty_*() = t._2)
      Orders.Table.items = pro().fills.~.sortBy(_.orderId).reverse.groupWith(_.orderId).map(t => {
        val (q, a, f) = t._2.sumFew(_.qnty, _.amount, _.fee)
        (t._1, q.toLong.Qnty, a, f)
      }).><

  // *************************************************************************
  object Orders extends Ui.Module.Listing[(Order.Id, Qnty, Amount, Amount)]:
    object Table extends Ui.Table[(Order.Id, Qnty, Amount, Amount)]:
      new Column[Order.Id]("Order", 55) { value_:(_._1) }
      new Column[Qnty]    ("Lots")      { value_:(_._2); QntyConfig(this) }
      new Column[Number]  ("A")         { value_:(_._3.index); IndexConfig(this) }
      new Column[Amount]  ("Fee")       { value_:(_._4); AmountConfig(this) }
