import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "macro-format"

version := "0.6"

scalaVersion := "2.10.4"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "com.lihaoyi" %% "utest" % "0.1.6" % Test

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.4"

conflictManager := ConflictManager("strict", "org.scala-lang" , "scala-reflect")
