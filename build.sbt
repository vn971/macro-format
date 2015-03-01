import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "scala-macro-log"

version := "0.9.2"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.11.2", "2.10.4")

organization := "net.pointsgame"

description := "logging with additional information from the Scala compiler"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

// fork := true

libraryDependencies += "com.lihaoyi" %% "utest" % "0.2.3" % Test

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)

conflictManager := ConflictManager("strict")

//scala.scalajs.sbtplugin.ScalaJSPlugin.scalaJSSettings
