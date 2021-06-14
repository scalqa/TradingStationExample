package com.datamata; package mkt; package ui; package base; package Trade; package Fill; import language.implicitConversions

trait Table[ROW] extends Quote.Table[ROW]:
  type VIEW <: M.Trade.Fill

  override protected def setupDefaultColumns: Unit =
    super.setupDefaultColumns
    new FeeColumn

  class FeeColumn extends Column[Amount]:
    label = "Fee"
    Ui.AmountConfig(this);
    valueView_:(_.fee)

