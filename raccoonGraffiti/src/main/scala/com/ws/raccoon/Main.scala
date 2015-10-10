package com.ws.raccoon

import java.io.{FileOutputStream, InputStream}

import com.ws.raccoon.services.RequestService
import org.apache.commons.io.IOUtils

/**
 * Created by agdubrov on 10/10/15.
 */
object Main {

  def main(args: Array[String]) {
    RequestService.storeHtml("http://solidskills.se");
  }

}
