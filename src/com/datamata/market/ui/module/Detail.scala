package com.datamata; package market; package ui; package module; import language.implicitConversions

abstract class Detail[A<:AnyRef] extends Module with Pro.OM.X.Base[A]:
  def this(v: A) = { this(); update(v) }

  def bindTo(v: Pro.O[_ <: A])                                : Event.Control = v.onChange(() => this() = v())
  def bindTo[B <: A](v: Idx.Selection.Observable[B], dflt: A) : Event.Control = v.onChangeRun { this() = v.value_? or dflt }
  def onChangeRun(f: => Unit)                                 : Event.Control = this.onChange(() => f)

