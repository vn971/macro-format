package net.pointsgame.macros

import utest._
import net.pointsgame.macros.Macro._

object MyTest extends TestSuite {
	val tests = TestSuite {

		'printTypeDoesntFail {
			printType("a" + null)
			printType(null)
		}
		'printType {
			assert(printType(1 + 2) == 3)
		}

		'prettyPrintSurvivesNulls {
			assert(prettyFormat() == "")
			assert(prettyFormat(null) == "null")
			assert(prettyFormat(1, 2, null, null) == "1, 2, null, null")
			assert(prettyFormat(if (1 == 1) null else null) contains "if ")
		}

		'prettyPrintWorksGood {
			assert(prettyFormat(1, 2) == "1, 2")
			assert(prettyFormat(1, "2") == "1, 2")

			val a = 1
			val b = 2
			assert(prettyFormat(a + b) == "a.+(b) = 3")

			val bigger = prettyFormat(if (2 > 1) "bigger" else "not bigger")
			assert(bigger contains "if (")
			assert(bigger endsWith "bigger")
		}

		'onlyWithCompileKey {
			// just test that the compiler won't crash.
			// If you want usages and "why-to"-s, go to README.txt
			val someHeavyTree = List(4, 3, 2, 1)
			onlyWithCompileKey {
				println(someHeavyTree.sorted)
			}
		}

	}
}
