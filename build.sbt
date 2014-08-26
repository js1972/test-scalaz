name := "scala-test"
 
version := "1.0"
 
scalaVersion := "2.11.1"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.0"

libraryDependencies += "org.julienrf" %% "play-json-variants" % "0.2"