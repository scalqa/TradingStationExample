package com.datamata.market; package app.trading_station; package accounts; import Live.*; import language.implicitConversions

object Actions:

  def stream(row: Row): ~[Fx.Action] = row.valueOpt.map {
    case t: Tape =>
      Stream(Fx.Action("Browser...", () => Fx.Stage("Tape Browser", 500, 300, new ui.base.tape.Detail(t)).self(_.scene.styleSheets += Ui.datamataCssUrl).show))

    case a: Account =>

      def menu(name: String, s: ~[Symbol]): Fx.Menu = {
        def add(v: Symbol) = Positions.add(row, v)
        Ui.MenuList[Symbol](name, s.sort, add, _.foreach(add))
      }

      def menuStatus(name: String, f: Position.Status => Boolean): Fx.Menu = menu(name, a.Positions.stream.take(v => f(v.status)).map(_.symbol))

      def cancel(f: Order => Boolean) = a.publishInfo(a.orderStream.take(f).load.peek(_.cancel).count +- " orders processed.")

      Stream[Fx.Action](
        SEPARATOR,
        menuStatus("All Positions", _ => true),
        menuStatus("Active", _.isActive),
        menuStatus("Closed", _.isClosed),
        menu("Potential", {
          val l = Positions.stream.take(_.accountRow == row).mapOpt(_.position.??.map(_.symbol)).pack
          a.Tape.Tickers.stream.map(_.symbol).take(_ == null).print
          a.Tape.Tickers.stream.map(_.symbol).sort.dropValues(l)
        }),
        Fx.Action("Clear all Non Active Orders ", () => cancel(!_.status.isActive)),
        Fx.Action("Cancel all Buys ", () => cancel(o => o.status.isSubmitted && o.qnty.isLong)),
        Fx.Action("Cancel all Sells ", () => cancel(o => o.status.isSubmitted && o.qnty.isShort)))
  } or VOID

