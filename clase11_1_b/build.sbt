name := "json-MongDB"
version := "1.0"
scalaVersion := "3.6.4"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongodb-driver-sync" % "4.10.2", // Driver Java sync
  "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
  "com.lihaoyi" %% "os-lib" % "0.9.1",
  "com.lihaoyi" %% "upickle" % "3.1.0"
  )
  
