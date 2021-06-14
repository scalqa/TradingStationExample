package com.datamata; package mkt; package ui; import language.implicitConversions

abstract class Table[A] extends Fx.Table[A]:

  _onRealCreated(_ => placeholder = new Fx.Pane { style = "-fx-background-color: black;" })


