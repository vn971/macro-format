package net.pointsgame.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.Try

object Macro {

	def prettyFormat(params: Any*): String = macro Macro.prettyFormat_impl
	def prettyFormat_impl(c: Context)(params: c.Expr[Any]*): c.Expr[String] = new Helper[c.type](c).prettyFormat(params: _*)

	def prettyPrint(params: Any*): Unit = macro Macro.prettyPrint_impl
	def prettyPrint_impl(c: Context)(params: c.Expr[Any]*): c.Expr[Unit] = new Helper[c.type](c).prettyPrint(params: _*)

	/** this method prints the type of the body at compile time
		* and has no effect at run time. */
	def typePrint[T](t: => T): T = macro Macro.printType_impl[T]
	def printType_impl[T: c.WeakTypeTag](c: Context)(t: c.Expr[T]): c.Expr[T] = new Helper[c.type](c).printType(t)

	def onlyWithCompileKey[T](body: T): Option[T] = macro Macro.onlyWithCompileKey_impl[T]
	def onlyWithCompileKey_impl[T: c.WeakTypeTag](c: Context)(body: c.Expr[T]): c.Expr[Option[T]] = {
		import c.universe._
		if (macroDebugExecutionEnabled) {
			reify(Some(body.splice))
		} else {
			reify(None)
		}
	}


	private val macroDebugExecutionEnabled = getCompileProperty("macro.compileWithDebug")
	private val verboseMacroExpand = getCompileProperty("macro.verboseExpand")

	private def getCompileProperty(p: String) = {
		def asBool(s: String) = Try(s.toBoolean).toOption
		sys.props.get(p).flatMap(asBool).
				orElse(sys.env.get(p).flatMap(asBool)).
				getOrElse(false)
	}


	/**
	 * This is a common technique to reuse macro methods within a project,
	 * see the official docs: http://docs.scala-lang.org/overviews/macros/overview.html#writing-bigger-macros
	 */
	private class Helper[C <: Context](val c: C) {
		import c.universe._
		def strExpr(s: String) = c.Expr[String](Literal(Constant(s)))

		private def fileLineNumber: String =
			c.enclosingPosition.source.file.name + ":" + c.enclosingPosition.line + ""

		def printType[T: c.WeakTypeTag](t: c.Expr[T]): c.Expr[T] = {
			println("" + fileLineNumber + " expression has type " + t.actualType)
			t
		}

		def prettyFormat(params: c.Expr[Any]*): c.Expr[String] = {
			val stringBuilderExpr = params.foldLeft {
				true -> reify(new scala.collection.mutable.StringBuilder)
				// boolean means "isHead", see below
			} { case ((isHead, left), param) =>

				val separatorAppended = if (isHead) left else reify(left.splice.append(", "))
				val syntaxTreeAppended = param.tree match {
					case Literal(Constant(_)) => separatorAppended
					case _ =>
						val tree = strExpr(param.tree.toString().replace('\n', ' '))
						reify(separatorAppended.splice.append(tree.splice).append(" = "))
				}
				val theValueAppended = reify(syntaxTreeAppended.splice.append(param.splice: Any))
				false -> theValueAppended
			}
			if (verboseMacroExpand) println(reify(stringBuilderExpr._2.splice.toString()))
			reify(stringBuilderExpr._2.splice.toString())
		}

		def prettyPrint(params: c.Expr[Any]*): c.Expr[Unit] =
			reify(println(strExpr(fileLineNumber).splice + " " + prettyFormat(params: _*).splice))

	}

}
