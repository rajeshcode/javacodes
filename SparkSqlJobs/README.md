To Build :
sbt package
or 
sbt assembly 

To execute:

/apache/spark-2.3.0-bin-hadoop2.7/bin/spark-submit   --class "SparkSqlJob" rajesh/SparkSqlJobs-assembly-0.1.jar  --master yarn  --queue hdlq-data-default

or 

/apache/spark-2.3.0-bin-hadoop2.7/bin/spark-submit   --class "SparkSqlJob" rajesh/sparksqljobs_2.11-0.1.jar  --master yarn  --queue hdlq-data-default

