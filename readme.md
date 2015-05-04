# User Activity Monitor

## Introduction
This project contains a simple user activity monitor on a single webpage. For every mouse move action a user makes on a webpage the coordinates will be sent to webserver using REST webservice. The webservice, writes the coordinates with the timestamp to Kafka topic. There will be a spark streaming processor running in background, which reads coordinates from Kafka topic, indentifies the Qadrant to which the coordinate belongs to and indexes the data to Elastic Search

## Technology stack
* Spring MVC
* Apache Spark (Streaming)
* Apache Kafka
* Elastic search

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

### Apache Spark



