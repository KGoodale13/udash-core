logLevel := Level.Warn

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.14")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.1")

libraryDependencies += "org.scala-js" %% "scalajs-env-selenium" % "0.1.3"
