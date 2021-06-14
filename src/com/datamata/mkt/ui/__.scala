package com.datamata; package mkt; import language.implicitConversions

import ui.context.*

object Ui:
  val ConfigPath_?   : Opt[J.Path] = access.Setup.DMDataPath + "UILayout"
  def datamataCssUrl : J.Url       = J.Url(this.getClass, "Datamata.css")

  export Fx.Paint.Color.Named.*
  export StyleClass.*

  def PositiveNegativePseudoGroup = Fx.Style.PseudoGroup.PositiveNegative
  def SmallMediumLargePseudoGroup = Fx.Style.PseudoGroup.SmallMediumLarge

  def PriceConfig       : ColumnConfig[Price]   = Config.Price
  def PlConfig          : ColumnConfig[Amount]  = Config.Pl
  def PlConfig_%        : ColumnConfig[Percent] = Config.Pl_%
  def AmountConfig      : ColumnConfig[Amount]  = Config.Amount
  def BriefAmountConfig : ColumnConfig[Amount]  = Config.BriefAmount
  def VolumeConfig      : ColumnConfig[Qnty]    = Config.Volume
  def LotConfig         : ColumnConfig[Qnty]    = Config.Lot
  def QntyConfig        : ColumnConfig[Qnty]    = Config.Qnty
  def PriceChangeConfig : ColumnConfig[Percent] = Config.PriceChange
  def IndexConfig       : ColumnConfig[Number]  = Config.Index
  def SymbolConfig      : ColumnConfig[Symbol]  = Config.Symbol
  def TimeConfig        : ColumnConfig[Time]    = Config.Time

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  type Application = ui.Application
  type MemoryPanel = ui.MemoryPanel
  type Table[A]    = ui.Table[A]
  type ChartPanel  = ui.ChartPanel; val ChartPanel = ui.ChartPanel
  /**/                              val Context    = ui.Context
  type Module      = ui.Module;     val Module     = ui.Module

  inline def MenuList = ui.MenuList