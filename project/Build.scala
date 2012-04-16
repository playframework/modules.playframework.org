import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "module-repo"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "be.objectify" % "deadbolt-2_2.9.1" % "1.1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
        resolvers += "Local Play Repository" at "file:///home/steve/development/play/play-2.0/repository/local"
    )

}
