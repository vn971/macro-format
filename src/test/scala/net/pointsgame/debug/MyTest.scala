package net.pointsgame.debug

import utest._
import net.pointsgame.debug.Debug._
import net.pointsgame.debug.Debug.onlyInDebug

object MyTest extends TestSuite {
	val tests = TestSuite {

		'onlyInDebugSimple {
			// doesn't really test anything -- just shows use case
			val someHeavyTree = List(4, 3, 2, 1)
			onlyInDebug {
				// TODO: explain what happens here!
				println(someHeavyTree.sorted)
			}
		}

		'printTypeDoesntFail {
			printType("a" + null)
			printType(null)
		}
		'printType {
			assert(printType(1 + 2) == 3)
		}

		'prettyPrintSurvivesNulls {
			assert(prettyPrint() == "")
			assert(prettyPrint(null) == "null")
			assert(prettyPrint(1, 2, null, null) == "1, 2, null, null")
			assert(prettyPrint(1, "2", null, null) == "1, 2, null, null")
			assert(prettyPrint(if (1 == 1) null else null) contains "if (true)")
		}

		'prettyPrintWorksGood {
			assert(prettyPrint("1") == "1")
			assert(prettyPrint(1) == "1")

			val a = 1
			val b = 2
			assert(prettyPrint(a + b) == "a.+(b) = 3")
		}

	}
}
