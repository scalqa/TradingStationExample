package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _9_Method:
  self: Table =>

  new TheColumn[String]("Method", 55, _.status.isActive) {
    useValueFromView(o => o.method.id.tag)
    //    real().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }


