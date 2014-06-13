package net.pointsgame.macros

import scala.language.experimental.macros
import scala.util.Try
import scala.reflect.macros.blackbox.Context
import scala.Some

object Macro {

	/** this method prints the type of the body at compile time
		* and has no effect at run time. */
	def printType[T](t: => T): T = macro Macro.printType_impl[T]

	def prettyFormat(params: Any*): String = macro Macro.prettyFormat_impl

	def onlyWithCompileKey[T](body: T): Option[T] = macro Macro.onlyWithCompileKey_impl[T]

	def printType_impl[T: c.WeakTypeTag](c: Context)(t: c.Expr[T]): c.Expr[T] = {
		System.out.println("Expression " + t.tree.toString() + " has type [" + t.actualType + "]")
		t
	}

	def prettyFormat_impl(c: Context)(params: c.Expr[Any]*): c.Expr[String] = {
		import c.universe._
		def strExpr(s: String) = c.Expr[String](Literal(Constant(s)))

		val stringBuilderExpr = params.foldLeft {
			// boolean means "isHead", see below
			true -> reify(new scala.collection.mutable.StringBuilder)
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

}
