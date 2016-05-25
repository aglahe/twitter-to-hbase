package com.aaron.hbase

/**
  * Created by aarong on 5/11/16.
  */
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark._
//import org.apache.hbase._

object HBaseWriteTest {
  def main(args: Array[String]) {

      val sparkConf = new SparkConf().setAppName("HBase Test Writer")
      val sc = new SparkContext(sparkConf)


      // Make some data
      val numberRdd = sc.parallelize(500 to 10000).map( i => (i.toString(), i+1, "Aaron"))

//      val hbaseConf = HBaseConfiguration.create()

  }
}
