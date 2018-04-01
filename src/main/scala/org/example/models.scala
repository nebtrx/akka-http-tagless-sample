package org

import java.util.UUID

import cats.Show

package object models{
  case class User(id: UUID, email: String, loyaltyPoints: Int) {
    def copyAndAddPoints(pointsToAdd: Int): User = {
      this.copy(loyaltyPoints = this.loyaltyPoints + pointsToAdd)
    }
  }

  implicit val showUser: Show[User] = Show.show(u => u.email)

  val testUser = User(UUID.randomUUID(), "user@email.com", 45)
}




