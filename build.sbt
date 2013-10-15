name := "proximo"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.1",
  "com.typesafe.akka" %% "akka-remote" % "2.2.1",
  "com.typesafe.akka" %% "akka-kernel" % "2.2.1",
  "mysql" % "mysql-connector-java" % "5.1.26"
)