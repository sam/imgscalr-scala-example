name := "imgscalr-scala-example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.2"

scalacOptions in ThisBuild ++= Seq(
    "-language:_",
    "-feature",
    "-unchecked",
    "-deprecation")

resolvers ++= Seq(
    "Maven Central" at "http://repo1.maven.org/maven2",
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= Seq(
    "org.imgscalr" % "imgscalr-lib" % "4.2")