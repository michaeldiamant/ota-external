val artifactoryHost = "ntoggle.jfrog.io"
val artifactoryCredentials = Credentials(
  realm = "Artifactory Realm",
  host = artifactoryHost,
  userName = System.getenv("ARTIFACTORY_USER"),
  passwd = System.getenv("ARTIFACTORY_PW"))
credentials += artifactoryCredentials
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
resolvers ++= Seq(
  "nToggle Plugin Snapshots" at
    "https://ntoggle.jfrog.io/ntoggle/plugins-snapshots-local",
  "nToggle Plugin Releases" at
    "https://ntoggle.jfrog.io/ntoggle/plugins-releases-local")