About
========

This library gives you logging with additional information from the Scala compiler. That includes:

 * The file name AND the line number, like "MySomething.scala:242"
Think for yourself if this would help you.

 * access to a textual representation of your own code.
This can give you interesting abilities like mapping
	format("authorizing", user.name, request.id)
	=> authorizing, user.name() = 13, request.id() = 17

 * Compatibility with old logging code.:) And libraries like slf4j, ch.qos.logback, log4j,..


Implementation
========

This library is called `scala-macro-log`.
It uses macros, 17 lines of code of it. It extracts information
from the Scala compiler and gives you the ability to log it.
Examples:

	log("hi")  ->  "hi"
	val a = 1
	log(a)  ->  "a = 1"
	val b = 1
	log(a + b)  ->  "a.+(b) = 3"

Other examples of real-working code can be seen in tests: MyTest.scala.

It's not actually necessary to deeply dive in this,
but if you're interested, here are the internals and
the macro-expanded output of a logging invocation:

	implicit def customLogger = ...
	log(i, user.id)
=>
	customLogger("MyTest.scala", 31, Seq(
		ParamInfo(i, "i", "Int", isConstant = false),
		ParamInfo(user.id, "user.id()", "Long", isConstant = false)
	))


Get Started
========

First, choose your version.
If you use scala-2.10 and you don't use ScalaJS -- v0.6_scala2.10
If you use scala-2.11 and you don't use ScalaJS -- v0.6_scala2.11
If you use scala-2.10 and you do use ScalaJS -- v0.6_scala2.10_sjs0.5.0
If you use scala-2.11 and you do use ScalaJS -- v0.6_scala2.11_sjs0.5.0

Ok, so you chose your version, for example v0.6_scala2.11

Now, add this to your build.sbt or Boot.scala:

	lazy val macroFormat = uri("git://gitorious.org/macro-format/macro-format.git#v0.6_scala2.11")
	lazy val root = Project("root", file(".")).dependsOn(macroFormat)

You can now use the macro itself:

	import net.pointsgame.macros.SimpleLog._
	val a = 2
	prettyFormat("hi", a + 1)

For a Maven project you have to: clone the repo, check-out the version you want,
do a publish-local and include the dependency in your project.


FAQ
========
Are there performance penalties?
	- There are some. For each `log` invocation
	a `scala.collection.Seq` would be constructed,
	and one `ParamInfo` for each of the parameters.
	The contents of the `Seq` and `ParamInfo` are pre-filled after compilation,
	so there are absolutely no additional operations above that.
	My personal point of view is that it's not much, but think for yourself, I gave you the details.

Could it be simpler?
	- Unfortunately, I don't think so.
	Some of the approaches are limited out by Scala macros limitations,
	some are too complex (I don't want that).


Inspiration
========

The main source of inspiration is: https://github.com/adamw/scala-macro-debug
It's a library that does macro-logging, too. How do we differ?

* this library has tests. The origin has none. o_O x_X

* this lib can be integrated with other loggers. The origin only works with System.out.

* macro invocation of `debug(1,2)` in the origin project got expanded to:
	print(1); print(", "); println(2);
Which is obviously wrong in a multi-threaded system.
Our implementation keeps the parameters together.

Summing the above, I thought it would be useful to write my own implementation,
and I hope it has its strong sides.


Future plans
========

Maybe AST printing, like "a + b" instead of "a.+(b)" ?


Other notes
========

Main git repo: https://gitorious.org/macro-format/macro-format
Github mirror: https://github.com/vn971/macro-format

(Gitorious is just a free software alternative to github.)


Copyright: Vasya Novikov 2014. License: GPL v3.
