import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "scala-macro-print"

version := "1.0"

scalaVersion := "2.11.0"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

fork := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies ++= Seq(
	"com.lihaoyi" %% "utest" % "0.1.4"
)
