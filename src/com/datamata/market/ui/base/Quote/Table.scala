package com.datamata; package market; package ui; package base; package quote; import language.implicitConversions

trait Table[ROW] extends Ui.Table[ROW]:
  type VIEW <: Quote

  protected def setupDefaultColumns: Unit = { QntyColumn(); PriceColumn(); TimeColumn(); ExchangeColumn() }

  class PriceColumn    extends Column[Price] { Ui.PriceConfig(this); useValueFromView(_.price) }
  class QntyColumn     extends Column[Qnty]  { Ui.QntyConfig(this);  useValueFromView(_.qnty) }
  class TimeColumn     extends Column[Time]  { Ui.TimeConfig(this);  useValueFromView(_.created) }
  class ExchangeColumn extends Column[Exchange]("Exch.", 75) {  useValueFromView(_.exchange); useFormat(_.name) }

