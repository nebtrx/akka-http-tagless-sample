package org.example.application.modules

import cats.Monad
import org.example.domain.algebras.{EmailDispatcher, UserRepository}
import org.example.application.services.UserService

trait UserModule[F[_]] {
  val userService: UserService[F]
}

class UserModuleImpl[F[_]: Monad](userInterpreter: UserRepository[F],
                                  emailInterpreter: EmailDispatcher[F]) extends UserModule[F] {
  
  override lazy val userService  = new UserService[F](userInterpreter, emailInterpreter)
}
