import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "module-repo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "deadbolt-2" %% "deadbolt-2" % "1.1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
        resolvers += "Objectify Play Repository" at "http://schaloner.github.com/releases/"
    )

}
