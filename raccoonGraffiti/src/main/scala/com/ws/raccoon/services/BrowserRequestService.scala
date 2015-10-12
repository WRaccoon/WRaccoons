package com.ws.raccoon.services

import java.io.File
import java.io.InputStream
import scala.concurrent._
import org.apache.commons.io.FileUtils
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlPage
import scala.collection.JavaConverters._
import akka.actor.ActorSystem
import akka.pattern.ask
import spray.client.pipelining._
import scala.util.Random

object BrowserRequestService {
  implicit val system = ActorSystem();
  import system.dispatcher // execution context for futures

  val webClient: WebClient = new WebClient(BrowserVersion.CHROME);
  val pipeline: String => Future[HtmlPage] = webClient.getPage(_);

  def storeHtml(site: String) {
    webClient.getOptions().setJavaScriptEnabled(true);
    webClient.getOptions().setActiveXNative(true);
    webClient.getOptions().setAppletEnabled(true);
    webClient.getOptions().setCssEnabled(true);
    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    // important!  Give the headless browser enough time to execute JavaScript
    // The exact time to wait may depend on your application.
    webClient.waitForBackgroundJavaScript(5000);

    storeHtml(site, "test");

    webClient.close();
    system.terminate();
  }

  def storeHtml(site: String, name: String) {

    try {
      val page: HtmlPage = webClient.getPage(site);
      page.getFrames().asScala.map { x => 
//        storeHtml(x.getFrameElement().getSrcAttribute(), x.getName + Random.nextInt);
      FileUtils.writeStringToFile(new File("/tmp/raccoon/" + x.getName + Random.nextInt + ".html"), x.getEnclosedPage.getWebResponse.getContentAsString)}
      FileUtils.writeStringToFile(new File("/tmp/raccoon/" + name + ".html"), page.asXml());
    } catch {
      case t: Throwable => t.printStackTrace();
    }
    //    val response: Future[HtmlPage] = pipeline(site);
    //
    //    var result: InputStream = null;
    //
    //    response.onSuccess {
    //      case htmlPage => {
    //        FileUtils.writeStringToFile(new File("/tmp/test.html"), htmlPage.asText());
    //        shutdown();
    //      }
    //      case _ => println("Failed")
    //    };
    //
    //    def shutdown(): Unit = {
    //      system.terminate()
    //    }
  }
}