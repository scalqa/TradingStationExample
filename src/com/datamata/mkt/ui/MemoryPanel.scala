package com.datamata; package mkt; package ui; import language.implicitConversions

import Fx.*

class MemoryPanel extends Pane.Grid:

  val used, max, free, total =  Label()

  add(1, 0, " Memory")
  add(2, 0, "  used:", used)
  add(3, 0, "  free:", free)
  add(4, 0, " total:", total)
  add(5, 0, "   max:", max)
  add(6, 0, Button("Garbage Collect", J.Vm.Memory.gc))

  Fx.Thread.scheduleEvery(1.Second, {
    used.text = J.Vm.Memory.used.toBrief
    total.text = J.Vm.Memory.total.toBrief
    free.text = J.Vm.Memory.free.toBrief
    max.text = J.Vm.Memory.max.toBrief
  })

