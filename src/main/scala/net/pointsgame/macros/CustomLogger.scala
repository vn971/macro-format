package net.pointsgame.macros

import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.mutable
import scala.language.experimental.macros


trait CustomLogger[T] {
	def apply(fileName: String, line: Int, params: Seq[ParamInfo]): T
}

case class ParamInfo(value: Any, syntaxTree: String, actualType: String, isConstant: Boolean)


object CustomLoggerExamples {

	/** Example output:
		* 20:16:35.939 MyTest.scala:60 - hello, a = 2, b = 3, a.+(b) = 5
		*/
	val stdout = new CustomLogger[Unit] {
		override def apply(file: String, line: Int, params: Seq[ParamInfo]): Unit =
			println(logToString(file, line, params))
	}

	private[macros] val logToString = new CustomLogger[String] {
		override def apply(file: String, line: Int, params: Seq[ParamInfo]): String = {
			val sb = new mutable.StringBuilder
			sb.append(dateFormatter.format(new Date()) + " ")
			sb.append(file + ":" + line + " - ")
			var isHead = true
			params.foreach { param =>
				if (!isHead) sb.append(", ")
				isHead = false
				if (!param.isConstant) {
					sb.append(param.syntaxTree)
					sb.append(" = ")
				}
				sb.append(param.value)
			}
			sb.toString()
		}
	}


	val dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS")
}

