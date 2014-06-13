import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "macro-format"

version := "0.4"

scalaVersion := "2.10.4"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies ++= Seq(
	"com.lihaoyi" %% "utest" % "0.1.4"
)
