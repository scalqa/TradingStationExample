package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object AccountProperties extends Ui.Module.Listing.Default(accountProperties.Table):

  Accounts.selection.onChange(s => Table.items = s.value_?.map_?(_.value_?.map(_.properties)) or \/)


