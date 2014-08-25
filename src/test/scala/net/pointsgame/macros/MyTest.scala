package net.pointsgame.macros

import utest._
import net.pointsgame.macros.Macro._

object MyTest extends TestSuite {
	val tests = TestSuite {

		'prettyPrintWorksGood {
			assert(prettyFormat(1, 2) == "1, 2")
			assert(prettyFormat(1, "2") == "1, 2")

			val a = 1
			val b = 2
			assert(prettyFormat(a + b) == "a.+(b) = 3")

			val bigger = prettyFormat(if (2 > 1) "bigger" else "less than expected")
			assert(bigger contains "if (")
			assert(bigger endsWith "bigger")
		}

		'prettyPrintSurvivesNulls {
			assert(prettyFormat() == "")
			assert(prettyFormat(null) == "null")
			assert(prettyFormat(1, 2, null, null) == "1, 2, null, null")
			assert(prettyFormat(if (1 == 1) null else null) contains "if ")
		}

		'printTypeDoesntFail {
			val i = 1
			typePrint(i)
			typePrint("a" + null)
			typePrint(null)
		}
		'printType {
			assert(typePrint(1 + 2) == 3)
		}

		'prettyPrintDoesntFail {
			prettyPrint()
			prettyPrint(1, 2)
			prettyPrint(null)
			prettyPrint("1" + 2)
			prettyPrint(if (1 == 1) null else null)
		}

		'onlyWithCompileKey {
			// just test that the compiler won't crash.
			// If you want usages and "why-to"-s, see README.txt
			val someHeavyTree = List(4, 3, 2, 1)
			onlyWithCompileKey {
				println(someHeavyTree.sorted)
			}
		}

	}
}
