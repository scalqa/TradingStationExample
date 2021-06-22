package com.datamata.market; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

transparent trait _positionColumns:
  self: Table =>

  new Column[String]("#", 5) { styleClass = AccountIdClass; value_:?(_.accountRow.id_?)  }
  new SymbolColumn           { format_:(s => s.ticker + (s.market.country.isCanada ? " *" or "")) }
  new Column[Price]          { valueView_:*(_.price_*); PriceConfig(this) }
  new Column[Qnty]           { valueView_:*(_.qnty_*); QntyConfig(this) }
  new Column[Number]("A")    { valueView_:*(_.amount_*.map_^(_.index)); IndexConfig(this) }
  new Column[Amount]         { label = "PPL"; valueView_:*(_.paperPl_*); PlConfig(this) }
  new Column[Amount]         { label = "BPL"; valueView_:*(_.bookedPl_*); PlConfig(this) }

