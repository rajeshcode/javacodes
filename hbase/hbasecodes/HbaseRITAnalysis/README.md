# HbaseRITAnalysis
Hbase Region In Tansition 


How to Execute :
Hadoop/hbase user in cli
su - hadoop

$ java   -Dlog4j.configuration=file:rajesh/conf/log4j.propertieshbase  -cp rajesh/HbaseRITAnalysis-3.0-SNAPSHOT.jar:`hbase classpath` com.hbase.region.HbaseRITAnalysis
