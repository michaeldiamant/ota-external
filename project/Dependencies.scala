object Dependencies {

  import sbt._

  object Rubicon {
    private def dtModule(name: String, module: String, version: String) =
      s"com.rubiconproject.dt.$name" %% s"$name-$module" % version

    val optimization = dtModule("optimization", "scala-with-java-conversions", "0.2.0")
  }

}