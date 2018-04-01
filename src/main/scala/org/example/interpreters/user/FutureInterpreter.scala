package org.example.interpreters.user

import java.util.UUID

import org.example.domain.algebras.UserRepository
import org.example.utils.Result
import org.models.{User, testUser}

import scala.concurrent.Future

trait FutureInterpreter extends UserRepository[Future] {
  override def findUser(id: UUID): Future[Result[User]] =
    Future.successful(Right(testUser))

  override def updateUser(u: User): Future[Result[User]] =
    Future.successful(Right(testUser)) /* as above */
}
