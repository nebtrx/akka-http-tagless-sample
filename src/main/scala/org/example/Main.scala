package org.example

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import monix.cats._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

object Main extends App {
  import org.example.api.services.UserHttpService
  import org.example.application.modules.UserModuleImpl
  import org.example.core.utils.FutureTransformableInstances._
  import org.example.interpreters.user.{TaskInterpreter => UserInterpreter}
  import org.example.interpreters.email.{TaskInterpreter => EmailInterpreter}

  implicit val actorSystem: ActorSystem = ActorSystem("my-system")
  implicit val streamMaterializer: ActorMaterializer = ActorMaterializer()
  val logger: LoggingAdapter = actorSystem.log

  // Composition Root
  val userInterpreter = new Object() with UserInterpreter
  val emailInterpreter = new Object() with EmailInterpreter
  val userModule = new UserModuleImpl(userInterpreter, emailInterpreter)

  // Supports composing several routes like:
  // ```
  //   XHttpServive.routes ~ YHttpServive.routes
  // ```
  val routes = UserHttpService[Task](userModule).routes

  // TODO: akka-http-circe vv
  // val fut: Future[Json] = Http().singleRequest(HttpRequest(uri = uri)).flatMap(Unmarshal(_).to[Json])

  val bindingM = Http().bindAndHandle(routes, "localhost", 6666)

  bindingM
    .map(_.localAddress)
    .map(addr => s"Bound to $addr")
    .foreach(logger.info)
}
