package net.pointsgame.macros

import scala.scalajs.js

object PlatformSpecific {

	def formatDate(ts: Long): String = {
		new js.Date(ts).toISOString()
	}

}
