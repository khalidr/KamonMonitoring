import com.typesafe.sbt.SbtAspectj._

name := """activator-akka-kamon"""

version := "2.3.12"

scalaVersion := "2.11.7"

val kamonVersion = "0.6.3"
val akkaHttpVersion = "10.0.0"
val akkaVersion = "2.4.14"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka"             %% "akka-slf4j"                       % akkaVersion,
  "ch.qos.logback"                % "logback-classic"                   % "1.1.7",
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-akka" % kamonVersion,
  "io.kamon" %% "kamon-statsd" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion,
  "io.kamon" %% "kamon-system-metrics" % kamonVersion,
  "org.aspectj" % "aspectjweaver" % "1.8.5",
  "io.kamon" %% "kamon-akka-http-experimental" % kamonVersion
)

aspectjSettings

javaOptions <++= AspectjKeys.weaverOptions in Aspectj

fork in run := true
