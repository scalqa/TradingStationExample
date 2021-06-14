package com.datamata; package mkt; package ui; import language.implicitConversions

abstract class Module extends Fx.Abstract.Module:
  protected type REAL = javafx.scene.Parent
  protected final override def _createReal : REAL                 = View.real
  protected          lazy  val View        : Fx.Abstract.Parent

object Module:
  type Detail[A<:AnyRef] = module.Detail[A]
  type Listing[A]        = module.Listing[A];    inline def Listing = module.Listing
