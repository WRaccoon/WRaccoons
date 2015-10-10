package com.raccoon.spark

import java.io.File
import java.io.FileWriter

import scala.io.Source

import org.specs2.mutable.Specification

import com.google.common.io.Files

class WordCountTest extends Specification {

  "A WordCount job" should {

    "count words correctly" in {

      val tempDir = Files.createTempDir()
      val inputFile = new File(tempDir, "input").getAbsolutePath
      val inWriter = new FileWriter(inputFile)
      inWriter.write("hack hack hack and hack")
      inWriter.close
      val outputDir = new File(tempDir, "output").getAbsolutePath

      WordCount.execute(
        master = Some("local"),
        args = List(inputFile, outputDir))

      val outputFile = new File(outputDir, "part-00000")
      val actual = Source.fromFile(outputFile).mkString
      actual must_== "(hack,4)\n(and,1)\n"
    }
  }
}