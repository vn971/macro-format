package net.pointsgame.macros

import scala.language.experimental.macros

trait Loggable {
	def log[T](params: Any*)(implicit logger: CustomLogger[T]): T = macro MacroImpl.logImpl[T]
}

object Loggable extends Loggable
