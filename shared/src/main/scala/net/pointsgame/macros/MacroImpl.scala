package net.pointsgame.macros

import scala.collection.mutable
import scala.language.experimental.macros
import scala.reflect.macros.Context

private[macros] object MacroImpl {

	def logImpl[T: c.WeakTypeTag](c: Context)(params: c.Expr[Any]*)(logger: c.Expr[CustomLogger[T]]): c.Expr[T] = {
		import c.universe._
		def const[Z](z: Z) = c.Expr[Z](Literal(Constant(z)))

		val fileName: c.Expr[String] = const(c.enclosingPosition.source.file.name)
		val line: c.Expr[Int] = const(c.enclosingPosition.line)

		// Now we should do smth like  params.map{p=> ParamInfo(...) }
		// Unfortunately, we can't do that stupidly because of a Scala limitation,
		// so we construct what we need step-by-step, converting to "Expression" each time
		val seq = params.foldLeft {
			reify(mutable.LinearSeq[ParamInfo]())
		} { case (sb, param) => reify(sb.splice :+ ParamInfo(
			param.splice,
			const(param.tree.toString().replace('\n', ' ')).splice,
			const(param.actualType.toString).splice,
			const(param.tree match {
				case Literal(_) => true
				case _ => false
			}).splice))
		}

		reify(logger.splice.apply(fileName.splice, line.splice, seq.splice))
	}

}
