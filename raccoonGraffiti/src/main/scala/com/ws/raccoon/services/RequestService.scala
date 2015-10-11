package com.ws.raccoon.services

/**
 * Created by agdubrov on 10/10/15.
 */

import java.io.{ByteArrayInputStream, InputStream}

import akka.io.IO
import org.apache.commons.io.{FileUtils, IOUtils}
import spray.can.Http
import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent._
import duration._
import java.io.File
import spray.util._


object RequestService {
  implicit val system = ActorSystem();
  import system.dispatcher // execution context for futures

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive;

  def storeHtml(site: String) {
    val response: Future[HttpResponse] = pipeline(Get(site));

    var result: InputStream = null;

    response.onSuccess {
      case HttpResponse(StatusCodes.OK, entity, _, _) => {
        FileUtils.writeByteArrayToFile(new File("/Work/test.html"), entity.data.toByteArray);
        shutdown();
      }
      case rsp: HttpResponse => println("Failure");
    };

    def shutdown(): Unit = {
      IO(Http).ask(Http.CloseAll)(1.second).await
      system.shutdown()
    }
  }

}
