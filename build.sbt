import scala.collection.immutable.Seq

lazy val root = (project in file("."))
  .settings(
    name := "central-reference-data-acceptance-tests",
    version := "0.1.0",
    scalaVersion := "3.6.3",
    libraryDependencies ++= Dependencies.test,
    (Compile / compile) := ((Compile / compile) dependsOn (Compile / scalafmtSbtCheck, Compile / scalafmtCheckAll)).value
  )
