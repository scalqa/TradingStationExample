package com.datamata; package market; package ui; package base; package trade; package Fill; import language.implicitConversions

trait Table[ROW] extends quote.Table[ROW]:
  type VIEW <: M.Trade.Fill

  override protected def setupDefaultColumns: Unit =
    super.setupDefaultColumns
    new FeeColumn

  class FeeColumn extends Column[Amount]:
    label = "Fee"
    Ui.AmountConfig(this);
    valueView_:(_.fee)

