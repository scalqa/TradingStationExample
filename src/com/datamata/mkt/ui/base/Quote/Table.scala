package com.datamata; package mkt; package ui; package base; package Quote; import language.implicitConversions

trait Table[ROW] extends Ui.Table[ROW]:
  type VIEW <: Quote

  protected def setupDefaultColumns: Unit = { QntyColumn(); PriceColumn(); TimeColumn(); ExchangeColumn() }

  class PriceColumn    extends Column[Price] { Ui.PriceConfig(this); valueView_:(_.price) }
  class QntyColumn     extends Column[Qnty]  { Ui.QntyConfig(this); valueView_:(_.qnty) }
  class TimeColumn     extends Column[Time]  { Ui.TimeConfig(this); valueView_:(_.created) }
  class ExchangeColumn extends Column[Exchange]("Exch.", 75) { valueView_:(_.exchange); format_:(_.name) }

