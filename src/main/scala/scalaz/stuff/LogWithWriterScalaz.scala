package scalaz.stuff

import scalaz._
import Scalaz._


object LogWithWriterScalaz {

  /**
   * Logging with a scalaz writer
   * Note that it would be better to use an ADT instead of List[String]
   * for real life logging.
   */
  def main(args: Array[String]): Unit = {
    //val k = args(0).toInt
    val k = 1

    val r: scalaz.Writer[List[String], Boolean] =
      for {
        a <- k.set(List("starting with " + k))
        b <- (a + 7).set(List("adding 7"))
        c <- scalaz.Writer(Nil: List[String], (b * 3))
        d <- c.toString.reverse.toInt.set(List("switcheroo with " + c))
        e <- (d % 2 == 0).set(List("is even?"))
      } yield e

    println("Result: " + r.value)
    println("LOG")
    println("===")
    r.written foreach println
  }
}