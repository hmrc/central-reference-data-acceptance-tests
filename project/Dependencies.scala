import sbt.*

object Dependencies {

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"       %% "api-test-runner"        % "0.9.0" % Test,
    "org.playframework" %% "play-ws-standalone-xml" % "3.0.7" % Test
  )

}
