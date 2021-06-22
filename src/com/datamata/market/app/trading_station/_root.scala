package com.datamata; package market; package app; package trading_station; import Live.*; import language.implicitConversions

export com.datamata.market.Ui.Context.*

val Accounts          = accounts.Accounts
val AccountProperties = accountProperties.AccountProperties
val Positions         = positions.Positions
val Orders            = orders.Orders
val Messages          = messages.Messages
val Bots              = bots.Bots
val Details           = details.Details;                                  type Details           = details.Details

val OrderRowEmptyStyle: Fx.Style    = "-fx-background-color: lightgrey;-fx-border-color: transparent;"
val BuyOrderBackgroundColor         = Fx.Color(0, 0, 150)
val SellOrderBackgroundColor        = Fx.Color(97, 0, 67)
val OrderBackgroundColor            = Fx.Color(192, 192, 192)
val OrderRowEmptyColor              = Fx.Color.LightGray
val AccountIdClass: Fx.Style.Class  = "market-trading-station-account-id"

extension (x: M.Order) def background: Fx.Style = if (x.isVoid) "" else if (x.qnty.isShort) "-fx-background-color: rgb(97, 0, 67)" else "-fx-background-color: rgb(0, 0, 150)"
