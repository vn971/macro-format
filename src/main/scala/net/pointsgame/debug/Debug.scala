package net.pointsgame.debug

import scala.language.experimental.macros
import scala.util.Try
import scala.reflect.macros.blackbox.Context
import scala.Some

object Debug {

	/** this method prints the type of the body at compile time
		* and has no effect on run time. */
	def printType[T](t: => T): T = macro Debug.printType_impl[T]

	def onlyInDebug[T](t: => T): Option[T] = macro Debug.onlyInDebug_impl[T]

	def prettyPrint(params: Any*): String = macro Debug.prettyPrint_impl

	def printType_impl[T: c.WeakTypeTag](c: Context)(t: c.Expr[T]): c.Expr[T] = {
		System.out.println("expression " + t.tree.toString() + " has type [" + t.actualType + "]")
		t
	}

	def prettyPrint_impl(c: Context)(params: c.Expr[Any]*): c.Expr[String] = {
		import c.universe._
		def strExpr(s: String) = c.Expr[String](Literal(Constant(s)))

		val stringBuilderExpr = params.foldLeft {
			// first boolean means "isHead", see below
			true -> reify(new scala.collection.mutable.StringBuilder)
		} { case ((isHead, left), param) =>

			val separatorAppended = if (isHead) left else reify(left.splice.append(", "))
			val syntaxTreeAppended = param.tree match {
				case Literal(Constant(_)) => separatorAppended
				case _ => reify(separatorAppended.splice.append(strExpr(param.tree.toString()).splice).append(" = "))
			}
			val theValueAppended = reify(syntaxTreeAppended.splice.append(param.splice: Any))
			false -> theValueAppended
		}
		if (verboseMacroExpand) println(reify(stringBuilderExpr._2.splice.toString()))
		reify(stringBuilderExpr._2.splice.toString())
	}

	def onlyInDebug_impl[T: c.WeakTypeTag](c: Context)(t: c.Expr[T]): c.Expr[Option[T]] = {
		import c.universe._
		if (macroDebugExecutionEnabled) {
			reify(Some(t.splice))
		} else {
			reify(None)
		}
	}


	private val macroDebugExecutionEnabled = !getCompileProperty("macro.debugExecution.enabled")
	private val verboseMacroExpand = getCompileProperty("macro.verboseExpand")

	private def getCompileProperty(p: String) = {
		def asBool(s: String) = Try(s.toBoolean).toOption
		sys.props.get(p).flatMap(asBool).
				orElse(sys.env.get(p).flatMap(asBool)).
				getOrElse(false)
	}

}
