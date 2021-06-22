package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object MainDemo extends Ui.Application(1500, 700, "Trading Station With Simulated Accounts"):
  lazy  val View = Layout

  lazySetup {

    /* persistSizeAndLocation */   // Do not want to write to disk for the demo

    Accounts.add("", Tape.Sim.^(_.connection.connect).^(_.generate(12)))

    Accounts.add("1", Account.Sim("Sim Account 1".AccountId, Tape.Sim).^(a => {
      a.connection.connect

      J.scheduleIn(1.Second, {
        a.addFill(200.Qnty,  Symbol.SPY, 7.Minutes + 11.Seconds)
        a.addFill(-100.Qnty, Symbol.SPY, 4.Minutes + 20.Seconds)
        a.Positions.get(Symbol.SPY).order(300.Qnty, 22.Price)
      })
    }))
    Accounts.last.^(a => J.scheduleIn(3.Seconds, Tape.Sim.Tickers.~.map(_.symbol).sort.foreach(Positions.add(a,_))))

    Accounts.add("2", Account.Sim("Sim Account 2".AccountId, Tape.Sim).^(_.connection.connect))
    Accounts.add("3", Account.Sim("Sim Account 3".AccountId, Tape.Sim).^(_.connection.connect))

  }
