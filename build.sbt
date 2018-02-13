name := "akka01-assignment-1"

version := "0.1"

scalaVersion := "2.12.4"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-actor
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.9"

// https://mvnrepository.com/artifact/com.typesafe.akka/akka-testkit
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.9" % Test

libraryDependencies += "log4j" % "log4j" % "1.2.17"