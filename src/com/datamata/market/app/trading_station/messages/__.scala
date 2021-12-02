package com.datamata.market; package app.trading_station; import Live.*; import language.implicitConversions

object Messages extends Ui.Module.Listing[Message] with Message.Service:

  Message.Service.Default.addMessageService(this)

  private val buffer = Idx.M[Message]() //new messages to be refreshed once a second

  def publish(m: Message) = this.synchronized {
    if (buffer.isEmpty) Fx.Thread.scheduleIn(1.Second, rows.addAllAt(0, this.synchronized { buffer.stream.load.self(_ => buffer.clear) }))
    buffer += m
  }

  protected object Table extends Ui.Table[Message]:
    new Column[String]("Message", 400, _.text) {
      useFormat(s => s.replaceAll("\n", " "))
      useStyleOpt(c => c.rowOpt.map(_.typ).mapOpt{
        case Message.Type.Error   => "red";
        case Message.Type.Warning => "blue"
        case Message.Type.Doc    => if (c.row.time.age < 30.Seconds) "white" else "grey"
      }.map("-real-text-fill:" + _: Fx.Style))
      alignment = LEFT
      useTooltip(v => v)
      useMouseClicked((e, c) => if (e.button.isLeft && e.clickCount == 2) new Fx.Popup {        children += Fx.Label(c.row.text)      }.show(c, 0, 0))
      useCellSetup(_.font = Fx.Text.Font("monospaced", 12))
    }

    new Column[String]("Context", 80, _.context) {
      style = "-real-text-fill:grey"
    }

    new Column[Boolean] {
      prefWidth = 35
      useValuePro(_.acknowledgedPro)
      useEditor(new CheckBox, _.acknowledged = _)
      graphic = Fx.Button("Ok", {
        val (ok, not) = rows.stream.countFew(_.acknowledged, !_.acknowledged)
        if (not == 0) rows.clear
        else if (ok == 0) rows.stream.foreach(_.acknowledged = true)
        else rows.removeFor(_.acknowledged)
      })
    }

    new Column[Time] {
      Ui.TimeConfig(this)
      style = "-real-text-fill:grey"
      prefWidth = 60
      useValue(_.time)
      refreshEvery(5.Seconds)
    }

