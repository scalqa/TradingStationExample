package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object AccountProperties extends Ui.Module.Listing.Default(accountProperties.Table):

  Accounts.selection.onChange(s => Table.items = s.valueOpt.mapOpt(_.valueOpt.map(_.properties)) or VOID)


