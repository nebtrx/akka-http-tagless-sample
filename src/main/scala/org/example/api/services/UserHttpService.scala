package org.example.api.services

import java.util.UUID

import akka.http.scaladsl.server.Directives.{complete, failWith, get, onComplete, path}
import akka.http.scaladsl.server.{Directive1, Route}
import cats.Monad
import org.example.application.modules.UserModule
import org.example.core.api.HttpService
import org.example.core.utils.FutureTransformable
import org.example.utils.Result
import org.models.User
import org.example.core.utils.FutureTransformableSyntax._

import scala.util.{Failure, Success, Try}

class UserHttpService[F[_]:Monad:FutureTransformable](userModule: UserModule[F]) extends HttpService {
  override def routes: Route =
    get {
      path("test") {
        val computation: F[Result[User]] = userModule.userService.getUser(UUID.randomUUID())
        onComplete(computation.asFuture) {
          // TODO: Add json serialization
          case Success(s: Result[User]) => complete(processContent(s))
          case Failure(ex: Throwable) => failWith(ex)
        }
      }
    }
}

object UserHttpService {
  def apply[F[_]:Monad:FutureTransformable](userModule: UserModule[F]): UserHttpService[F] =
    new UserHttpService[F](userModule)
}
