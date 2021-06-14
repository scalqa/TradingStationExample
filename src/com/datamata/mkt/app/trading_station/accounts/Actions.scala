package com.datamata.mkt; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

object Actions:

  def ~(row: Row): ~[Fx.Action] = row.value_?.map {
    case t: Tape =>
      ~~(Fx.Action("Browser...", () => Fx.Stage("Tape Browser", 500, 300, new ui.base.Tape.Detail(t)).show))

    case a: Account =>

      def menu(name: String, s: ~[Symbol]): Fx.Menu = {
        def add(v: Symbol) = Positions.add(row, v)
        Ui.MenuList[Symbol](name, s.sort, add, _.foreach(add))
      }

      def menuStatus(name: String, f: Position.Status => Boolean): Fx.Menu = menu(name, a.Positions.~.take(v => f(v.status)).map(_.symbol))

      def cancel(f: Order => Boolean) = a.publishInfo(a.order_~.take(f).load.peek(_.cancel).count +- " orders processed.")

      ~~[Fx.Action](
        SEPARATOR,
        menuStatus("All Positions", _ => true),
        menuStatus("Active", _.isActive),
        menuStatus("Closed", _.isClosed),
        menu("Potential", {
          val l = Positions.~.take(_.accountRow == row).map_?(_.position.^.?.map(_.symbol)).><
          a.Tape.Tickers.~.map(_.symbol).take(_ == null).print
          a.Tape.Tickers.~.map(_.symbol).sort.dropAll(l)
        }),
        Fx.Action("Clear all Non Active Orders ", () => cancel(!_.status.isActive)),
        Fx.Action("Cancel all Buys ", () => cancel(o => o.status.isSubmitted && o.qnty.isLong)),
        Fx.Action("Cancel all Sells ", () => cancel(o => o.status.isSubmitted && o.qnty.isShort)))
  } or \/

