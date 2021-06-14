package com.datamata.mkt; package app.trading_station; package accountProperties; import Live.*; import language.implicitConversions

object AccountProperties extends Ui.Module.Listing.Default(Table):

  Accounts.selection.onChange(s => Table.items = s.get_?.map_?(_.value_?.map(_.properties)) or \/)


