package com.datamata.market; package app.trading_station; package positions; package table; import Live.*; import language.implicitConversions

transparent trait _positionColumns:
  self: Table =>

  new Column[String]("#", 5) { styleClass = AccountIdClass; useValueOpt(_.accountRow.idOpt)  }
  new SymbolColumn           { useFormat(s => s.ticker + (s.market.country.isCanada ? " *" or "")) }
  new Column[Price]          { useValueFromViewPro(_.pricePro); PriceConfig(this) }
  new Column[Qnty]           { useValueFromViewPro(_.qntyPro); QntyConfig(this) }
  new Column[Number]("A")    { useValueFromViewPro(_.amountPro.mapView(_.index)); IndexConfig(this) }
  new Column[Amount]         { label = "PPL"; useValueFromViewPro(_.paperPlPro); PlConfig(this) }
  new Column[Amount]         { label = "BPL"; useValueFromViewPro(_.bookedPlPro); PlConfig(this) }

