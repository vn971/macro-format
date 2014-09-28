package net.pointsgame.macros


/** a logger for this hobby project. */
object HobbyProjectLogger extends Loggable {
	implicit def stdoutLogger = CustomLoggerExamples.stdout
}

import HobbyProjectLogger._

object HobbyProjectExample {
	log("hello")
}
