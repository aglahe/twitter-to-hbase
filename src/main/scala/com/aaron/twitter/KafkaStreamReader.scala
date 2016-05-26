package com.aaron.twitter

/**
  * Created by aarong on 5/13/16.
  */
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka.KafkaUtils
import org.json4s._
import org.json4s.jackson.JsonMethods._

import org.apache.log4j.Logger

import it.nerdammer.spark.hbase._

object KafkaStreamReader extends LogHelper {

    def main(args: Array[String]) {

        if (args.length < 4) {
            System.err.println("Usage: KafkaToHabse <broker-list> <group.id> <topic> <hbase-table>")
            System.exit(1)
        }

        logger.info("Setting up conf...")
        val conf = new SparkConf().setAppName("Twitter to HBase")
        val ssc = new StreamingContext(conf, Milliseconds(500))

        logger.info("Set up Kafka Parms..")
        val Array(brokers, groupid, topic, hbaseTable) = args
//        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers, "group.id" -> groupid,  "auto.offset.reset" -> "smallest")
        val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers, "group.id" -> groupid)
        val topics = Set[String](topic)

        logger.info("Setting up stream...")
        val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)

        // Get the value, and convert the RDD[String] into a DataFrame using JSON
        stream.map(_._2).foreachRDD(rdd => {
            if (!rdd.isEmpty) {  // If the RDD isn't empty, go ahead and process
                // Parse JSon in to a JValue and the ori
                val jsonRdd = rdd.map(l => (parse(l), l))

                // Create KV RDD with _1 being the rowkey
                // We need to put the formats = DefaultFormats in here because of a bug in json4s 3.2.10/11
                val kvRdd = jsonRdd.map(v => {implicit val formats = DefaultFormats;
                    (DatatypeConverter.printHexBinary(md5((v._1\"user"\"id").extract[String]))+"."+(v._1\"id").extract[String], v._2)})

                // Write to HBase
                logger.debug("writing 1st rowkey: "+ kvRdd.first()._1)
                kvRdd.toHBaseTable(hbaseTable).toColumns("data").inColumnFamily("d").save()
            }
            else {
                logger.debug("Rdd was empty")
            }
        })

        // Start the fun
        logger.info("Start processing...")
        ssc.start()
        ssc.awaitTermination()
    }

    def md5(s: String) = {
        MessageDigest.getInstance("MD5").digest(s.getBytes)
    }
}

trait LogHelper {
    lazy val logger = Logger.getLogger(getClass.getName)
}