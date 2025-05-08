name := "insertar-MongDB"
version := "1.0"
scalaVersion := "3.6.4"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongodb-driver-sync" % "4.10.2", // el driver de MongoDB para Java o Scala 
  "com.typesafe.play" %% "play-json" % "2.10.0-RC5")
