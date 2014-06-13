addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0") // eclipse with-source=true

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0") // gen-idea


resolvers += Resolver.url("scalaJs-sbt-releases",
  url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
    Resolver.ivyStylePatterns)

resolvers += Resolver.url("scalaJs-sbt-snapshots",
  url("http://repo.scala-js.org/repo/snapshots/"))(
    Resolver.ivyStylePatterns)

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.0")

//addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.1.6")
