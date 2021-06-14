package com.datamata; package mkt; package ui; import context.*; import language.implicitConversions

trait Context:

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


object Context:
  type ColumnConfig[A] = context.ColumnConfig[A]
  val  StyleClass      = context.StyleClass
  val  Config          = context.Config

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

  val MathFormat = Math.Format("##.##")
