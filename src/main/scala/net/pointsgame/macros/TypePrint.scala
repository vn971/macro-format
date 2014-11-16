package net.pointsgame.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context


/** You didn't see this class.
	*
	* Well OK, you saw it.
	* I'm just too lazy to open-up a separate project for a single method,
	* and that single method I do find useful.
	*
	* It prints the type of an expression at compile time (useful if your IDE got mad).
	*/
object TypePrint {
	def typePrint(body: Any): Unit = macro TypePrintImpl.printType_impl
}

private[macros] object TypePrintImpl {
	def printType_impl(c: Context)(body: c.Expr[Any]): c.Expr[Unit] = {
		println(c.enclosingPosition.source.file.name + ":" + c.enclosingPosition.line + " expression has type " + body.actualType)
		c.universe.reify(Unit)
	}
}
