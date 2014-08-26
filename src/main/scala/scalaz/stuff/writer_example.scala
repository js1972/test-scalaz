package scalaz.stuff

import scalaz._
import Scalaz._


object WriterLog {
  type LOG = List[String]
}

import WriterLog._

case class Writer[A](log: LOG, a: A) {
  def map[B](f: A => B): Writer[B] =
    Writer(log, f(a))

  def flatMap[B](f: A => Writer[B]): Writer[B] = {
    val Writer(log2, b) = f(a)
    Writer(log ::: log2 /* accumulate */, b)
  }
}

object Writer {
  implicit def LogUtilities[A](a: A) = new {
    def nolog =
      Writer(Nil /* empty */, a)

    def withlog(log: String) =
      Writer(List(log), a)

    def withvaluelog(log: A => String) =
      withlog(log(a))
  }
}



object writer_example {
  import Writer._
  
  def main(args: Array[String]): Unit = {
    //val k = args(0).toInt
    val k = 1
    
    val r =
      for {
        a <- k withvaluelog ("starting with " + _)
        b <- (a + 7) withlog "adding 7"
        c <- (b * 3).nolog
        d <- c.toString.reverse.toInt withvaluelog ("switcheroo with " + _)
        e <- (d % 2 == 0) withlog "is even?"
      } yield e

    println("Result: " + r.a)
    println("LOG")
    println("===")
    r.log foreach println
  }

}