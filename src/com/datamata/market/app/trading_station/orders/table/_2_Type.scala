package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _2_Type:
  self: Table =>

  new TheColumn[Order.Type]("Type", 60, _.status.isSubmitted) {
    valueView_:(_.orderType);
    updateTrigger_:(_.order.status_*)
    styleClass = "dflt"
    // onEdit(row = row.isDataRow() && row().status().isNew(), () = new ComboBoxEditor() {items().addAll(J.Order.Type.V.LIMIT.all());    onCommit((o, v, ov) = o.type().set(v)) });
    new AskCell[Amount](_.paperPl_*, PlConfig)
    new TrdCell[Symbol](_.symbol_*, SymbolConfig) // { alignment = CENTER }
    new BidCell[Amount](_.bookedPl_*, PlConfig)
  }

