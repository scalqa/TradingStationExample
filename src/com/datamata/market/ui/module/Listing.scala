package com.datamata; package market; package ui; package module; import language.implicitConversions

abstract class Listing[A] protected extends Module with Idx[A]:
  protected lazy  val Table                              : Fx.Table[A]
  protected lazy  val View                               : Fx.Abstract.Parent = new Fx.Pane.Border { center = Table; eventStore.removeProperty_?[Fx.Node.Like](Listing.StatusBar).forval(bottom = _)}
  protected       def statusBar_:(sb: => Fx.Node.Like)   : Unit            = eventStore.setProperty_?(Listing.StatusBar, sb)
  protected       def rows                               : Idx.OM[A]       = Table.rows

  /**/            def items                              : Idx[A]          = Table.items
  /**/            def selection                          : Fx.Selection[A] = Table.selection
  /**/            def apply(i: Int)                      : A               = rows(i)
  /**/            def size                               : Int             = rows.size

object Listing:
  private object StatusBar

  class Default[A](t: Fx.Table[A]) extends Listing[A]:
    protected lazy  val Table = t


