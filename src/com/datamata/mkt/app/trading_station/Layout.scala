package com.datamata.mkt; package app.trading_station; import Live.*; import language.implicitConversions; import Fx.*

object Layout extends Pane.Split:
  orientation = HORIZONTAL

  add(Positions, 57.Percent)

  add(new Pane.Split {
    orientation = VERTICAL

    add(new Pane.Split {
      orientation = HORIZONTAL
      add(Accounts, 32.Percent)
      add(AccountProperties, 30.Percent)
      add(Bots)
    }, 20.Percent)
    add(Messages, 10.Percent);
    add(Orders, 20.Percent)
    add(Details)
  })


