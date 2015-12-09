package net.pointsgame.macros


/** Import this object and you're ready to go.
  *
  * (More examples can be seen in tests.)  */
object SimpleLog extends Loggable {
	implicit def stdoutLogger = CustomLoggerExamples.stdout
}
