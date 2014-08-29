About
========

The main target of this library is logging and loggers, but from an unusual perspective.
What may you want from a logger? I know this:


 * The file name AND the line number should be known for ease of reading.
For example, "MySomething.scala:242"
Think for yourself if such a logging capability would help you.


 * It would be nice to allow write less, and then even less code.
For example,

	println("authorizing, user.name = " + user.name + ", request id = " + request.id) =>
	println("authorizing, user.name = ${user,name}, request id = ${request.id}") =>
	prettyPrint("authorizing", user.name, request.id)

Well, that last line is actually possible.
Using scala macros, we can create a logger that would output the following:
    authorizing, user.name() = 13, request.id() = 17


 * It would be practically useful if such a logger could be integrated
into your/mine old logging code,
and already-present loggers like slf4j, ch.qos.logback, log4j,..


Implementation
========

This library is called `scala-format`, has ~83 lines of code and:

* a formatting method. Examples:
	prettyFormat("hi")  ->  "hi"
	val a = 1
	prettyFormat(a)  ->  "a = 1"
	val b = 1
	prettyFormat(a + b)  ->  "a.+(b) = 3"
	prettyFormat("constant string", user.id, request.id)  ->  "constant string, user.id() = 13, request.id() = 17"

* a simple printing method. Examples:
	prettyPrint(1, 2, "hi")
	// when invoked, this method will print:
	// 23:59:47.717 MyTest.scala:31  1, 2, hi

* a more complex logger that allows you to bind this library to your own logger.
It's basically the same as "prettyPrint", but allows you to customize
the output, and even customize the side-effect of logging. So, for example,
you can send some message to some akka-actor system instead.

* there are also 2 small additional methods.
Just for fun, and not exactly related to logging.
Might be useful though, check them if you found the logging part interesting.


Other examples of real-working code can be seen in tests: MyTest.scala.


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

	import net.pointsgame.macros.Macro._
	val a = 2
	prettyFormat("hi", a + 1)

For a Maven project you have to: clone the repo, check-out the version you want,
do a publish-local and include the dependency in your project.


Inspiration
========

The main source of inspiration is: https://github.com/adamw/scala-macro-debug
It's a library that does macro-logging, too. How do we differ?

* this library has tests. The origin has none. o_O x_X

* this lib can be integrated with other loggers while the origin cannot be.
In fact, the origin always prints to System.out

* this library is smarter about println-s.
In the origin project, macro invocation of `debug(1,2)` got expanded to:
	print(1); print(", "); println(2);
This is obviously wrong in a multi-threaded system.
In our implementation, the code is expanded to
	new stringBuilder().apply(1: Any).apply(", ").apply(2: Any).toString()

summing the above, I thought it would be useful to write my own implementation,
and I hope it has its strong sides.


Future plans
========

There were some plans, but got implemented, and other were thrown out.
I may add other simple-stupid macros in the future, if I find any that would be nice and useful.


Other notes
========

Main git repo: https://gitorious.org/macro-format/macro-format
Github mirror: https://github.com/vn971/macro-format

(Gitorious is just a free software alternative to github.)


Copyright: Vasya Novikov 2014. License: GPL v3.
