package org.example.interpreters.email

import monix.eval.Task
import org.example.domain.algebras.EmailDispatcher
import org.example.utils.Result

trait TaskInterpreter extends EmailDispatcher[Task] {
  override def sendEmail(email: String, subject: String, body: String): Task[Result[Unit]] = {
    // this is for showing the action happened purposes
    println("####### Email sent!!!!")
    Task.now { Right((): Unit) }
  }
}
