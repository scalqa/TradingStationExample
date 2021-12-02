package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object Bots extends Ui.Module.Listing.Default(bots.Table):

  Accounts.selection.onChange(s => Table.rows.replaceWith(s.valueOpt.mapOpt(_.accountOpt).map(_.bots.stream) or VOID))
