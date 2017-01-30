package sample.kamon

import java.net.InetAddress

import akka.actor.ActorSystem
import akka.actor._
import MessageGenerator._
import RandomNumberActor._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import kamon.akka.http.KamonTraceDirectives._

import scala.io.StdIn
import kamon.Kamon

import scala.collection.Searching.search
import scala.collection.mutable

object Main extends App {

  Kamon.start()

  implicit val system = ActorSystem("application")
  implicit val materializer = ActorMaterializer()

  implicit val ec = system.dispatcher
println("fooo!!!!")
  //lazy val collectionContext = Kamon.metrics.buildDefaultCollectionContext

  val route =
    path("hello") {
      traceName("helloTrace"){

        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }
    }~
    path("metric"){
      complete(Kamon.metrics.find("yourapp","trace").get.collect(collectionContext).metrics.toString)
    }




  /*val numberGenerator = system.actorOf(Props[RandomNumberActor], "numbers")

  val generator = system.actorOf(Props[MessageGeneratorActor], "artifical")

  generator ! ConstantLoad(Schedule(numberGenerator, GenerateNumber, 5000))
  generator ! ConstantLoad(Schedule(numberGenerator, GenerateSecureNumber, 1000))
  generator ! Peak(Schedule(numberGenerator, GenerateNumber, 100000))
  generator ! Peak(Schedule(numberGenerator, GenerateSecureNumber, 25000))

*/


  val bindingFuture = Http().bindAndHandle(route, InetAddress.getLoopbackAddress.getHostAddress, 9999)

/*
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done
*/

 /* var myActors = mutable.HashSet[ActorRef]()
  for (i <- 0 to 10) {
    myActors += system.actorOf(Props(new TestActor))
  }
  for (actor <- myActors) {
    actor ! 1000000
  }*/
  // Kamon.shutdown()

}
class TestActor extends Actor {
  override def receive: Receive = {
    case v: Int =>
      for (i <- 0 until 10000000) {
        if (i % 10000001 == 0) {
          println("temp")
        }
      }
  }
}