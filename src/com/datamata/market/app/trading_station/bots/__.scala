package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object Bots extends Ui.Module.Listing.Default(bots.Table):

  Accounts.selection.onChange(s => Table.rows.replaceAll(s.get_?.map_?(_.account_?).map(_.bots.~) or \/))
