import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "module-repo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "be.objectify" %% "deadbolt-2" % "1.1.2",
      "rome" % "rome" % "1.0",
      "org.markdownj" % "markdownj" % "0.3.0-1.0.2b4"
    )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings {
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns)
    resolvers += Resolver.url("Scala Tools", url("http://scala-tools.org/repo-releases/"))(Resolver.ivyStylePatterns)
  }

}
