package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

class Table extends Ui.Table[Row] with _1_Account with _2_Type with _3_Clear with _4_Lots with _5_Price with _6_Offset with _7_Transmit with _8_Expire with _9_Method:
  type VIEW = Order; view_:?(_.order_?)

  this.sortMode = \/

  def position_? = Orders.position_?

  new CustomRow(EMPTY) {
    style = OrderRowEmptyStyle
  }

  new CustomRow(_.row_?.take(_.order_?)) {
    mouseClicked_:((e, c) => if (e.button.isLeft && e.clickCount == 3) println(c.row.order))
    updateTrigger_:(_.order.status_*)
  }

  abstract class TheColumn[CV](label_ :String, width_ : Int, showBackground: Order => Boolean = \/)(using o: Opt[Ordering[CV]], i: Any.Def.Void[CV]) extends Column[CV](label_, width_)(using o)(using i):
    sortable  = false
    alignment = CENTER
    style_:?(_.view_?.map(o => if (o.status.notClosed && showBackground(o)) o.background else ("-fx-background-color: rgb(192, 192, 192)": Fx.Style)))
    new CustomCell(EMPTY) { style = OrderRowEmptyStyle }

    // ************************************************************************************************************
    import Ui.Context.ColumnConfig
    class BidCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isBid, f, c)
    class TrdCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isLast, f, c)
    class AskCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isAsk, f, c)

    // ***********************************************************************************************************************
    class PositionCell[T](tf: Quote.Type => Boolean, f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends CustomCell[T](r => tf(r.asInstanceOf[Row].serviceType)):
      if (f != null) on_*(f)
      if (c != null) c(this)

      def onVal(f: Position => T)       = value_:?(_.position_?.map(f(_)))
      def on_*(f: Position => Pro.O[T]) = value_:*?(_.position_?.map(f).map(_.map_^(_.?)) or ??? )
