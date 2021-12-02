package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

class Table extends Ui.Table[Row] with _1_Account with _2_Type with _3_Clear with _4_Lots with _5_Price with _6_Offset with _7_Transmit with _8_Expire with _9_Method:
  type VIEW = Order; useViewOpt(_.orderOpt)

  this.sortMode = VOID

  def positionOpt = Orders.positionOpt

  new CustomRow(EMPTY) {
    style = OrderRowEmptyStyle
  }

  new CustomRow(_.rowOpt.take(_.orderOpt)) {
    useMouseClicked((e, c) => if (e.button.isLeft && e.clickCount == 3) println(c.row.order))
    refreshOn(_.order.statusPro)
  }

  abstract class TheColumn[CV](label_ :String, width_ : Int, showBackground: Order => Boolean = VOID)(using o: Opt[Ordering[CV]], i: Any.Def.Void[CV]) extends Column[CV](label_, width_)(using o)(using i):
    sortable  = false
    alignment = CENTER
    useStyleOpt(_.viewOpt.map(o => if (o.status.notClosed && showBackground(o)) o.background else ("-fx-background-color: rgb(192, 192, 192)": Fx.Style)))
    new CustomCell(EMPTY) { style = OrderRowEmptyStyle }

    // ************************************************************************************************************
    import Ui.Context.ColumnConfig
    class BidCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isBid, f, c)
    class TrdCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isLast, f, c)
    class AskCell[T](f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends PositionCell[T](_.isAsk, f, c)

    // ***********************************************************************************************************************
    class PositionCell[T](tf: Quote.Type => Boolean, f: Position => Pro.O[T] = null, c: ColumnConfig[T] = null) extends CustomCell[T](r => tf(r.asInstanceOf[Row].serviceType)):
      if (f != null) onPro(f)
      if (c != null) c(this)

      def onVal(f: Position => T)       = useValueOpt(_.positionOpt.map(f(_)))
      def onPro(f: Position => Pro.O[T]) = useValueProOpt(_.positionOpt.map(f).map(_.mapView(_.?)) or ??? )
