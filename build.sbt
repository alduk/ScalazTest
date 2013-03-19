name := "ScalazTest"
     
version := "1.0"
     
scalaVersion := "2.10.0"
     
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
     
libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"  withSources() withJavadoc()

libraryDependencies += "junit" % "junit" % "4.11" % "test" withSources() withJavadoc()

libraryDependencies += "org.scalaz" % "scalaz-core_2.10" % "7.0.0-M9" withSources() withJavadoc()
 
scalacOptions += "-feature"
 
initialCommands in console := "import scalaz._, Scalaz._"

