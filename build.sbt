
name := "scala-macro-log"

version := "0.9.3"

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.11.5", "2.10.4")

organization := "net.pointsgame"

description := "logging with additional information from the Scala compiler"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

// fork := true

libraryDependencies += "com.lihaoyi" %% "utest" % "0.3.0" % Test

testFrameworks += new TestFramework("utest.runner.Framework")

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)

//enablePlugins(ScalaJSPlugin)
