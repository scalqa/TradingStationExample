package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _2_Type:
  self: Table =>

  new TheColumn[Order.Type]("Type", 60, _.status.isSubmitted) {
    useValueFromView(_.orderType);
    refreshOn(_.order.statusPro)
    styleClass = "dflt"
    // onEdit(row = row.isDataRow() && row().status().isNew(), () = new ComboBoxEditor() {items().addAll(J.Order.Type.V.LIMIT.all());    onCommit((o, v, ov) = o.type().set(v)) });
    new AskCell[Amount](_.paperPlPro, PlConfig)
    new TrdCell[Symbol](_.symbolPro, SymbolConfig) // { alignment = CENTER }
    new BidCell[Amount](_.bookedPlPro, PlConfig)
  }

