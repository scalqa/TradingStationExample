package com.datamata.market; package app.trading_station; package orders; package table; import Live.*; import language.implicitConversions

transparent trait _3_Clear:
  self: Table =>

  new TheColumn[Order.Status]("", 50, _.status.isSubmitted){

    valueView_:*(_.status_*)

    format_: {
      case v if !v.isClosed             => "C"
      case Order.Status.ClosedFilled    => "fill"
      case Order.Status.ClosedCancelled => "canc"
      case _                            => "clsd"
    }

    onCellChange(refreshRow(_))

    graphic = Fx.Button("Clear", position_?.forval(_.localOrders.~.drop(_.status.isActive).load.foreach(_.cancel)))

    def statusStyle(f: Order.Status => Boolean, style: String) = style_:?(_.view_?.map(_.status).take(f).map(_ => style:Fx.Style))

    statusStyle( _.isSubmitted, "-fx-background-color: yellow")
    statusStyle(! _.isClosed, "-fx-text-fill: red")
    statusStyle( _.isPending, "-fx-border-color: white; -fx-border-width: 2px");
    mouseClicked_:((e, c) => if (e.button.isLeft) c.view_?.take(_.status.notClosed).forval(o => if (o.status.isUpdated) o.refresh else o.cancel));

    new AskCell
    new TrdCell(_.qnty_*, QntyConfig){ format_:(_.^.?.map(_.tag)) }
    new BidCell
  }

