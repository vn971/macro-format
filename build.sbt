import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "macro-format"

version := "0.5"

scalaVersion := "2.11.1"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

// fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "com.lihaoyi" %% "utest" % "0.1.6"
