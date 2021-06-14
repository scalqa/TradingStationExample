package com.datamata; package mkt; package ui; package context; import language.implicitConversions

class ColumnConfig[A](name: String, width: Int, f: Fx.Table.Cell.Setup[_, _, A] => Unit):
  def this(name: String, f: Fx.Table.Cell.Setup[_, _, A] => Unit) = this(name, 50, f)

  def apply[E, W](setup: Fx.Table.Cell.Setup[E, W, _ <: A]): Unit = setup match
    case v: Fx.Table[_]#Column[_] =>
      v.label_*().^.?.fornil{ v.label_*() = name }
      v.prefWidth = width
      f(v.cast[Fx.Table[_]#Column[A]])
    case v: Fx.Table.Cell.Setup[_, _, _] =>
      f(v.cast[Fx.Table.Cell.Setup[_, _, A]])

