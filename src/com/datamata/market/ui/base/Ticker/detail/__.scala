package com.datamata; package market; package ui; package base; package Ticker; package detail; import language.implicitConversions

class Detail[A <: M.Ticker] extends Ui.Module.Detail[A]:
  val stats  : Stats   = new Stats().^(_.bindTo(this))
  val quotes: Quotes = new Quotes().^(_.bindTo(this))
  val data  : Data   = new Data(true).^(_.bindTo(this))

  val statsTab = new Fx.Pane.Tab.Panel("Stats", stats)

  protected object View extends Fx.Pane.Tab:
    add(statsTab)
    add("Quotes", quotes)
    add("Data", data)

  this.onChangeRun(statsTab.text= this().symbol.toString)


object Detail:
  type Data   = detail.Data
  type Stats   = detail.Stats
  type Quotes = detail.Quotes

