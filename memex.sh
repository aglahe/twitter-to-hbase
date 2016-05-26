#!/bin/bash

bin/spark-submit \
  --master yarn \
  --deploy-mode client \
  --num-executors 16 \
  --executor-memory 40G \
  --executor-cores 4 \
  --packages org.apache.spark:spark-streaming-kafka_2.11:1.6.1,org.json4s:json4s-jackson_2.11:3.2.11,it.nerdammer.bigdata:spark-hbase-connector_2.10:1.0.3 \
  --class com.aaron.twitter.KafkaStreamReader \
  --conf spark.hbase.host=zk01.xdata.data-tactics-corp.com,zk02.xdata.data-tactics-corp.com,zk03.xdata.data-tactics-corp.com,zk04.xdata.data-tactics-corp.com,zk05.xdata.data-tactics-corp.com \
  twittertohbase_2.11-1.0.jar \
  10.105.0.11:9092,10.105.0.13:9092,10.105.0.15:9092,10.105.0.17:9092,10.105.0.19:9092,10.105.0.21:9092,10.105.0.23:9092,10.105.0.25:9092,10.105.0.27:9092 \
  aarong-streaming \
  twitter \
  aaron-t1
