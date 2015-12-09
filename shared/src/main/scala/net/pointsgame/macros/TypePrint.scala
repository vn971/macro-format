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

	def typePrint[T](body: T): T = macro TypePrint.typePrint_impl[T]

	def typePrint_impl[T: c.WeakTypeTag](c: Context)(body: c.Expr[T]): c.Expr[T] = {
		println(c.enclosingPosition.source.file.name + ":" + c.enclosingPosition.line + " expression has type " + body.actualType)
		body
	}
}
