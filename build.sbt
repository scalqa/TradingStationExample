
scalaVersion := "3.1.0"

fork in run  := true

scalaSource in Compile := baseDirectory.value / "src"

lazy val needJavaFx = {
  var jv = System.getProperty("java.version")
  if(jv.startsWith("1.")) jv = jv.substring(2,jv.length-2)
  if(jv.startsWith("8.") || jv.startsWith("9.") || jv.startsWith("10.")) "none"
  else System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _                            => throw new Exception("Unknown platform!")
  }
}

libraryDependencies ++= Seq("base", "controls", "graphics").filter(_ => needJavaFx != "none").map( m=> "org.openjfx" % s"javafx-$m" % "11.0.2" classifier needJavaFx)

libraryDependencies += "org.scalqa" % "scalqa_3" % "0.995"

mainClass in (Compile, run) := Some("com.datamata.market.app.trading_station.MainDemo")
