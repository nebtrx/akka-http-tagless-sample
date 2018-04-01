package org.example.interpreters.user

import java.util.UUID

import monix.eval.Task
import org.example.domain.algebras.UserRepository
import org.example.utils.Result
import org.models.{User, testUser}

trait TaskInterpreter extends UserRepository[Task] {
  override def findUser(id: UUID): Task[Result[User]] =
    Task.now { Right(testUser) }

  override def updateUser(u: User): Task[Result[User]] =
    Task.now { Right(testUser)} /* as above */
}
