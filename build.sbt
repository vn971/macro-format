import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

name := "macro-format"

version := "0.6"

scalaVersion := "2.11.1"

organization := "net.pointsgame"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

EclipseKeys.withSource := true

testFrameworks += new TestFramework("utest.runner.JvmFramework")

libraryDependencies += "com.lihaoyi" %% "utest" % "0.1.6" % Test

scala.scalajs.sbtplugin.ScalaJSPlugin.scalaJSSettings

//(loadedTestFrameworks in Test) := {
//	(loadedTestFrameworks in Test).value.updated(
//		sbt.TestFramework(classOf[JsFramework].getName),
//		new JsFramework(environment = (jsEnv in Test).value)
//	)
//}
//
//testLoader := JSClasspathLoader((execClasspath in Compile).value)

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.1"

conflictManager := ConflictManager("strict", "org.scala-lang" , "scala-reflect")
