package org.example

package object utils {
  type Result[T] = Either[String, T]
}
