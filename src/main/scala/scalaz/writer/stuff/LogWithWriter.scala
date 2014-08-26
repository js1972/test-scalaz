package scalaz.writer.stuff

import WriterLog._

object WriterLog {
  type LOG = List[String]
}

/**
 * The writer data structure and monad
 */
case class MyWriter[A](log: LOG, a: A) {
  def map[B](f: A => B): MyWriter[B] =
    MyWriter(log, f(a))

  def flatMap[B](f: A => MyWriter[B]): MyWriter[B] = {
    val MyWriter(log2, b) = f(a)
    MyWriter(log ::: log2 /* accumulate */, b)
  }
}

object MyWriter {
  implicit def LogUtilities[A](a: A) = new {
    def nolog =
      MyWriter(Nil /* empty */, a)

    def withlog(log: String) =
      MyWriter(List(log), a)

    def withvaluelog(log: A => String) =
      withlog(log(a))
  }
}


/**
 * A demonstration of using the writer monad
 */
object LogWithWriter {
  import MyWriter._
  
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