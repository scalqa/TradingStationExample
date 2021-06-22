package com.datamata; package market; package ui; import language.implicitConversions

abstract class Application(width: Int.Opt, height: Int.Opt, name: String, show: Boolean = true) extends Fx.Application(width, height, name, show):
  self =>

  lazySetup {
    println(Ui.datamataCssUrl)
    self.scene.styleSheets += Ui.datamataCssUrl
  }

  // --------------------------------------------------------------------
  def saveLayoutToJson: J.Object = J.Object.M() += ("width", self.stage.width) += ("height", self.stage.height) += ("x", self.stage.x) += ("y", self.stage.y)

  def loadLayoutFromJson(o: J.Object): Unit =
    self.stage.width = o.double("width");
    self.stage.height = o.double("height");
    self.stage.x = o.double("x");
    self.stage.y = o.double("y")

  def persistSizeAndLocation: Unit = Ui.ConfigPath_?.map(_ + (name.replace(" ", "_").trim.lower + ".json")).map(_.file).forval(persistSizeAndLocation)

  def persistSizeAndLocation(file: J.File): Unit =
    file.path.parent.make
    if (file.exists) J.Json.parseObject_??(file.readString).forval(loadLayoutFromJson)
    val l: () => Any = () => file.writeString(saveLayoutToJson.toString)
    self.stage.x_*.onChange(l)
    self.stage.y_*.onChange(l)
    self.stage.width_*.onChange(l)
    self.stage.height_*.onChange(l)

