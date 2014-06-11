import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "scala-macro-print"

version := "1.0"

scalaVersion := "2.10.3"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

fork := true

libraryDependencies ++= Seq(
)
