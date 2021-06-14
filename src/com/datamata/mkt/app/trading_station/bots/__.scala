package com.datamata.mkt; package app.trading_station; package bots; import Live.*; import language.implicitConversions

object Bots extends Ui.Module.Listing.Default(Table):

  Accounts.selection.onChange(s => Table.rows.replaceAll(s.get_?.map_?(_.account_?).map(_.bots.~) or \/))
