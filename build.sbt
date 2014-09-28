import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "scala-macro-log"

version := "0.7"

scalaVersion := "2.11.1"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

// fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "com.lihaoyi" %% "utest" % "0.1.6" % Test

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.1"

conflictManager := ConflictManager("strict", "org.scala-lang" , "scala-reflect")
