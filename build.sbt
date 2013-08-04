name := "project name"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12.3" % "test",
  "com.github.scala-incubator.io" % "scala-io-core_2.9.1" % "0.4.1-seq",
  "com.github.scala-incubator.io" % "scala-io-file_2.9.1" % "0.4.1-seq"
)

scalacOptions += "-deprecation"

compile <<= (compile in Compile) map {
  result => {
    Process("ctags-exuberant -Re").run
    result
  }
}
