package net.pointsgame.macros

import scala.language.experimental.macros


/** a logger for this "production" project. */
object ProductionProjectLogger extends Loggable {

	private def verbositySpecific(verbosity: String) = new CustomLogger[Unit] {
		override def apply(file: String, line: Int, params: Seq[ParamInfo]): Unit = {

			// wait, maybe the first step would be to send the raw (unformatted) data to some akka system?

			// or maybe we just invoke our old slf4j/logback logger,
			// feeding it with prettified parameters like:
			// "authorizing, user.id() = 67, request.id() = 15" ?

			println(params.map(_.value).mkString(", "))
		}
	}

	val trace = verbositySpecific("TRACE")
	val info = verbositySpecific("INFO ")
	val error = verbositySpecific("ERROR")
	implicit val debug = verbositySpecific("DEBUG")
	// we could leave the "implicit" so that each log invocation would have to explicitly set debug/info/..
}


import ProductionProjectLogger._
object ProductionProjectExample {
	log("hello")
	log("authorized")(info)
	log(1, 2, "3" * 3)
	log("transaction failed")(error)
}
