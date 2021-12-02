package com.datamata.market; package app.trading_station; package orders; import Live.*; import language.implicitConversions

class Row(val positionRow: Positions.Row, val serviceType: Quote.Type, val orderOpt : Opt[Live.Order]) extends Comparable[Row] with Able.Doc {
  def this(pe: Positions.Row, st: Quote.Type) = this(pe, st, VOID)
  def this(pe: Positions.Row, o: Live.Order) = this(pe, Quote.Type.Void, o)

  def order: Live.Order = orderOpt.get

  def positionOpt = positionRow.position.?

  def compareTo(that: Row) = { val i = this.sortingBase - that.sortingBase; if (i != 0) i else java.lang.Double.compare(that.order.price.toDouble, this.order.price.toDouble) }

  def doc = Doc(this) += ("serviceType", serviceType) += ("Price", orderOpt.map(_.price.tag) or "") += ("sortingBase", sortingBase) += ("Status", orderOpt.map(_.status.toString) or "")

  private def sortingBase: Int = serviceType match {
    case Quote.Type.Ask => 3
    case Quote.Type.Bid => 5
    case Quote.Type.Last => 4
    case _ if order.qnty.isLong => if (order.status.isClosed) 7 else 6
    case _ if order.status.isClosed => 1
    case _ => 2
  }
}
