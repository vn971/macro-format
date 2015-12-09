// some additional boilerplate is needed to support ScalaJs. It doesn't affect JVM packages.

name in ThisBuild := "scala-macro-log"
version in ThisBuild := "0.10.0"
organization in ThisBuild := "net.pointsgame"
description in ThisBuild := "logging with additional information from the Scala compiler"
scalaVersion in ThisBuild := "2.11.7"
crossScalaVersions in ThisBuild := Seq("2.11.7", "2.10.6")
scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-feature")

lazy val macroLog = crossProject.in(file("."))
	.settings(
		testFrameworks += new TestFramework("utest.runner.Framework"),
		libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)
	)
	.jvmSettings(
		fork := true,
		libraryDependencies += "com.lihaoyi" %% "utest" % "0.3.1" % Test
	).jsSettings(
		libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.1" % Test
	)

lazy val macroLogJVM: Project = macroLog.jvm
lazy val macroLogJS: Project = macroLog.js
