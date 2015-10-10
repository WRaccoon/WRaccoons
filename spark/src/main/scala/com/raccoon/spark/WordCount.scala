package com.raccoon.spark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

object WordCount {
  
  private val AppName = "WordCountJob"

  def execute(master: Option[String], args: List[String], jars: Seq[String] = Nil) {
  
    val sc = {
      val conf = new SparkConf().setAppName(AppName).setJars(jars)
      for (m <- master) {
        conf.setMaster(m)
      }
      new SparkContext(conf)
    }
   
    // Adapted from Word Count example on http://spark-project.org/examples/
    val file = sc.textFile(args(0))
    val words = file.flatMap(line => tokenize(line))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.saveAsTextFile(args(1))
  }

  // Split a piece of text into individual words.
  private def tokenize(text : String) : Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")
  }
}