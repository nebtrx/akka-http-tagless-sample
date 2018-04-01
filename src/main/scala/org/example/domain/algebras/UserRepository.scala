
package org.example.domain.algebras

import java.util.UUID

import org.example.utils.Result
import org.models.User

import scala.language.higherKinds


// Algebra / Operations
trait UserRepository[F[_]] {
  def findUser(id: UUID): F[Result[User]]
  def updateUser(u: User): F[Result[User]]
}
