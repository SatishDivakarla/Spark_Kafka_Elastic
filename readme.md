# User Activity Monitor

## Introduction
This project contains a simple user activity monitor on a single webpage. For every mouse move action a user makes on a webpage the coordinates will be sent to webserver using REST webservice. The webservice, writes the coordinates with the timestamp to Kafka topic. There will be a spark streaming processor running in background, which reads coordinates from Kafka topic, indentifies the Qadrant to which the coordinate belongs to and indexes the data to Elastic Search

## Technology stack
* Spring MVC
* Apache Spark (Streaming)
* Apache Kafka
* Elastic search
* Maven

## Installations and getting started
### Kafka
- Download Kafka distribution from https://www.apache.org/dyn/closer.cgi?path=/kafka/0.8.2.0/kafka_2.10-0.8.2.0.tgz
- Extract the tar file <br/>
  > tar -xzf kafka_2.10-0.8.2.0.tgz <br/>
  > cd kafka_2.10-0.8.2.0
- Start zookeeper <br/>
  > bin/zookeeper-server-start.sh config/zookeeper.properties 
- Start Kafka server <br/>
  > bin/kafka-server-start.sh config/server.properties 
- Create kafka topic "useractivity" <br/>
  > bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic useractivity <br/>
- Verify that the kafka topic is created <br/>
  > bin/kafka-topics.sh --list --zookeeper localhost:2181 <br />
  
### Elastic Search
- Download the zip, TAR, RPM or DEB file from the following link https://www.elastic.co/downloads/elasticsearch
- Run bin/elasticsearch on Unix or bin/elasticsearch.bat on Windows to start elastic search
- ElasticSearchUtil class expects the cluster.name and node.name to "elastictest" and "SatishD" resply. Please change the elastic.yml file in config to match it or change this file accordingly(for now). This will be picked from the properties file in future.

### Apache Spark
- Download the spark from the following page http://spark.apache.org/downloads.html. For convenience you can download prebuilt with hadoop2.6 binary
- Extract the tar file
-  Go to spark home
-  Run the following command
  > bin/spark-submit --class com.useractivity.spark.streaming.StreamProcessor --master local[4] <location to activitystreamprocessor-1.0-SNAPSHOT-jar-with-dependencies.jar>




