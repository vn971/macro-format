package net.pointsgame.macros

import net.pointsgame.macros.CustomLoggerExamples._
import net.pointsgame.macros.Loggable._
import utest._


/** These are tests. If you want code examples -- see other files in this directory. */
object MyTest extends TestSuite {
	val tests = TestSuite {
		val a = 2
		val b = 3

		'easy_cases {
			implicit def implicitLogToString = logToString
			assert(log(2, 3) endsWith "2, 3")
			assert(log(2, "3") endsWith "2, 3")
			assert(log(a + b) endsWith "a.+(b) = 5")
		}

		'does_not_crash {
			log()(logToString)
			log(null, null, "", null, null)(logToString)
			()
		}

		'if_statement {
			val result = log(if (2 > 1) "2" else "1")(logToString)
			assert(result contains "if (")
			assert(result endsWith "= 2")
		}

		'usage_as_extractor {
			val extractionLogger = new CustomLogger[(String, Int, Seq[ParamInfo])] {
				override def apply(file: String, line: Int, params: Seq[ParamInfo]) = (file, line, params)
			}

			val hello = "hello"
			val first = log(hello, a, 2)(extractionLogger)
			val second = log(hello, a, 2)(extractionLogger)

			'file_name {
				assert(first._1 == "MyTest.scala")
			}
			'line_number {
				assert(first._2 > 10)
				assert(second._2 < 300)
				assert(second._2 == first._2 + 1) // each `log` has its own line number!
			}
			'types {
				val firstTypes = first._3.map(_.actualType)
				assert(firstTypes == Seq("String", "Int", "Int(2)"))
			}
		}

		'hobby_project_example {
			import net.pointsgame.macros.SimpleLog._
			// import the SimpleLog and you're ready to go:
			log(a, b, "hello", a + b)
		}

	}
}
