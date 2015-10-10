package com.ws.raccoon.services

/**
 * Created by agdubrov on 10/10/15.
 */

import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem


object RequestService {
  implicit val system = ActorSystem();
  import system.dispatcher // execution context for futures


  

}
