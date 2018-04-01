package org.example.core.api

import akka.http.scaladsl.server.Route
import cats.Show
import cats.syntax.show._
import org.example.utils.Result

trait HttpService {
  def routes: Route

  protected def processContent[T:Show](r: Result[T]): String =  r match {
    case (Left (e:String))  => e
    case (Right (u: T))     => u.show
  }
}
