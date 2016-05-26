name := "TwitterToHbase"
version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies += "org.apache.kafka" %% "kafka" % "0.9.0.1"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.9.0.1"

//libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.2.1"
//libraryDependencies += "org.apache.hbase" % "hbase-server" % "1.2.1"
//libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.2.1"
//libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.0.0-cdh5.5.4"
//libraryDependencies += "org.apache.hbase" % "hbase-server" % "1.0.0-cdh5.5.4"
//libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.0.0-cdh5.5.4"

libraryDependencies += "it.nerdammer.bigdata" % "spark-hbase-connector_2.10" % "1.0.3"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.1"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.11"

libraryDependencies += "log4j" % "log4j" % "1.2.17"
libraryDependencies +=  "org.scalatest" %% "scalatest" % "2.2.4" % "test"

resolvers += "cloudera-repos" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
javacOptions ++= Seq("-source", "1.7", "-target", "1.7", "-Xlint")
