package org.example.core.utils

import monix.execution.Scheduler.Implicits.global
import monix.eval.Task

import scala.concurrent.Future

trait FutureTransformable[F[_]] {
   def transformToFuture(f: F[_]):Future[_]
}

object FutureTransformableInstances {
  implicit val taskFutureTransformable = new FutureTransformable[Task] {
    def transformToFuture(task: Task[_]):Future[_] = task.runAsync
  }
}

object FutureTransformableSyntax {
  implicit class FutureTransformableOps[F[_]](value: F[_]) {
    def asFuture(implicit p: FutureTransformable[F]): Future[_] =
      p.transformToFuture(value)
  }
}



