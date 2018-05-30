name := "SparkSqlJobs"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "mysql" % "mysql-connector-java" % "5.1.6"
)


// This statement includes the assembly plug-in capabilities
//assemblySettings

// Configure JAR used with the assembly plug-in
//jarName in assembly := "my-project-assembly.jar"

// A special option to exclude Scala itself form our assembly JAR, since Spark
// already bundles Scala.
//assemblyOption in assembly :=
//  (assemblyOption in assembly).value.copy(includeScala = false)

//assemblyOption in assembly := (assemblyOption in assembly).value
 // .copy(includeScala = true)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}
