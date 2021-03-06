name := "scala-test"
 
version := "1.0"
 
scalaVersion := "2.11.2"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.1.0"

libraryDependencies += "org.scalaz" %% "scalaz-iteratee" % "7.1.0"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.3.0"

libraryDependencies += "org.julienrf" %% "play-json-variants" % "0.2"

libraryDependencies ++= Seq("com.chuusai" %% "shapeless" % "2.0.0")
