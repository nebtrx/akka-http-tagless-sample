package org.example.domain.algebras

import org.example.utils.Result

trait EmailDispatcher[F[_]] {
  def sendEmail(email: String, subject: String, body: String): F[Result[Unit]]
}

