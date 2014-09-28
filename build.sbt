import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "scala-macro-log"

version := "0.8"

scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.11.1", "2.10.4")

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

// fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "com.lihaoyi" %% "utest" % "0.1.6" % Test

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)

conflictManager := ConflictManager("strict")
