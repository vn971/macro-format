package net.pointsgame.macros

import java.text.SimpleDateFormat
import java.util.Date

object PlatformSpecific {

	def formatDate(ts: Long): String = {
		new SimpleDateFormat("HH:mm:ss.SSS").format(new Date(ts))
	}

}
