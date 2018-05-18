import sbt.Keys._
import Dependencies._
import com.ntoggle.pierre.{NtoggleDefaults, Dependencies => Pd}

val protocSettings = Seq(

  libraryDependencies ++= Seq(
    Rubicon.optimization % "protobuf",
    Rubicon.optimization
  ),

  Compile/PB.includePaths +=
    // extracted from the dependency jar via "protobuf" libraryDependency classifier above
    target.value / "protobuf_external" / "com" / "rubiconproject" / "dt" / "optimization" / "proto",

  Compile/sourceManaged :=
    (Compile/sourceDirectory).value / "generated",

  Compile/PB.targets := Seq(
    PB.gens.java ->
      (Compile/sourceManaged ).value,
    scalapb.gen(javaConversions = true, flatPackage = true) ->
      (Compile/sourceManaged).value
  ),

  // publish generated sources
  // as per https://github.com/sbt/sbt/issues/2205
  Compile/packageSrc/mappings ++= {
    import Path.{relativeTo, flat}

    val srcs = (Compile/managedSources).value
    val sdirs = (Compile/managedSourceDirectories).value
    val base = baseDirectory.value
    (srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)
  }
)

val commonSettings = NtoggleDefaults.dtSettings ++ {
  val heapSize = "2G"
  Seq(
    compileOrder := CompileOrder.Mixed,
    publishMavenStyle := true,
    javaOptions ++= Seq(s"-Xms$heapSize", s"-Xmx$heapSize"),

    // suppress `redundant cast to com.rub.dt.hell.mod.*.java.*.Builder` warnings
    // until i can figure out why it's being generated that way and how to fix it
    javacOptions -= "-Xlint",
    javacOptions += "-Xlint:none"
  )
}

val root = (project in file("."))
  .settings(name := "ota-external")
  .settings(commonSettings: _*)
  .settings(publishArtifact := false)
  .settings(protocSettings: _*)
  .settings(Defaults.itSettings: _*)
  .disablePlugins(sbtassembly.AssemblyPlugin)