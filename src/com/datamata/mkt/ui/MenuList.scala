package com.datamata; package mkt; package ui; import language.implicitConversions

object MenuList:

  def apply[A](name: String, vs: ~[A], onSelect: A => Unit, onAll: ~[A] => Unit) = new Fx.Menu {

    val vList = vs.><
    text = name + ": " + vList.size;
    enable = !vList.isEmpty

    object FxList extends Fx.List[A] {

      onMouseClicked(_ => selection.at_?(0).forval(onSelect))

      def reload(f: String) = FxList.items.replaceAll(vList.~.take(_.toString.startsWith(f)))
    }

    items.add(new Fx.Menu.Item.Custom(new Fx.Pane.Split(VERTICAL) {
      add(Fx.Pane.Flow(
        Fx.Label("Filter"),
        Fx.Text.Field("").^(_.onChange(c => if (c.isTextChange) FxList.reload(c.textAfter))),
        Fx.Button("Get All", onAll(vList))), 5.Percent)

      add(FxList)
    }))

    FxList.reload("")
  }
