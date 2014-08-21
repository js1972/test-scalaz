package scalaz.stuff

import scalaz._
import Scalaz._

object writer {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(157); 

  def isBigGang(x: Int): (Boolean, String) =
    (x > 9, "Compared gang size to 9.")
    
  // Below we have generalised the commented-out PairOps class to make
  // the log a Monoid instead of a String.
  //
  //implicit class PairOps[A](pair: (A, String)) {
  //  def applyLog[B](f: A => (B, String)): (B, String) = {
  //    val (x, log) = pair
  //    val (y, newlog) = f(x)
  //    (y, log ++ newlog)
  //  }
  //}
  implicit class PairOps[A, B: Monoid](pair: (A, B)) {
    def applyLog[C](f: A => (C, B)): (C, B) = {
      val (x, log) = pair
      val (y, newlog) = f(x)
      (y, log |+| newlog)
    }
  };System.out.println("""isBigGang: (x: Int)(Boolean, String)""");$skip(548); val res$0 = 
  
  isBigGang(10);System.out.println("""res0: (Boolean, String) = """ + $show(res$0));$skip(43); val res$1 = 
  (3, "Smallish gang.") applyLog isBigGang;System.out.println("""res1: (Boolean, String) = """ + $show(res$1));$skip(26); val res$2 = 
  3.set("Smallish gang.");System.out.println("""res2: scalaz.Writer[String,Int] = """ + $show(res$2));$skip(156); val res$3 = 
  
  // these methods are injected to all types (by Scalaz._) and can be
  // used to create Writers
  // SET and TELL create Writers!
  3.set("something");System.out.println("""res3: scalaz.Writer[String,Int] = """ + $show(res$3));$skip(19); val res$4 = 
  "something".tell;System.out.println("""res4: scalaz.Writer[String,Unit] = """ + $show(res$4));$skip(92); val res$5 = 
  
  // Helper to create a WriterT Monad - replaces MonadWriter
  MonadTell[Writer, String];System.out.println("""res5: scalaz.MonadTell[scalaz.Writer,String] = """ + $show(res$5));$skip(41); val res$6 = 
  MonadTell[Writer, String].point(3).run;System.out.println("""res6: scalaz.Id.Id[(String, Int)] = """ + $show(res$6));$skip(149); 
  
  
  
  // example of using for syntax with Writer
  def logNumber(x: Int): Writer[List[String], Int] =
    x.set(List("got number: " + x.shows));System.out.println("""logNumber: (x: Int)scalaz.Writer[List[String],Int]""");$skip(18); val res$7 = 
  
  logNumber(3);System.out.println("""res7: scalaz.Writer[List[String],Int] = """ + $show(res$7));$skip(15); val res$8 = 
  logNumber(5);System.out.println("""res8: scalaz.Writer[List[String],Int] = """ + $show(res$8));$skip(116); 
  
  def multWithLog: Writer[List[String], Int] = for {
    a <- logNumber(3)
    b <- logNumber(5)
  } yield a * b;System.out.println("""multWithLog: => scalaz.Writer[List[String],Int]""");$skip(21); val res$9 = 
  
  multWithLog run;System.out.println("""res9: scalaz.Id.Id[(List[String], Int)] = """ + $show(res$9));$skip(436); 
  
  
  // *** >>= (flatMap) is how you append to a Writer ***
  
  // GCD example
  // Each time .tell is called on the List[String] the Writer
  // is logging the value (List[String]).
  def gcd(a: Int, b: Int): Writer[List[String], Int] =
    if (b == 0) for {
        _ <- List("Finished with " + a.shows).tell
      } yield a
    else
      List(a.shows + " mod " + b.shows + " = " + (a % b).shows).tell >>= { _ => gcd(b, a % b) };System.out.println("""gcd: (a: Int, b: Int)scalaz.Writer[List[String],Int]""");$skip(19); val res$10 = 
  
  gcd(8, 3).run;System.out.println("""res10: scalaz.Id.Id[(List[String], Int)] = """ + $show(res$10));$skip(202); val res$11 = 
  
  // Lists can be slow: see table of performance:
  // http://docs.scala-lang.org/overviews/collections/performance-characteristics.html
  // Speed tests further below...
  
  Monoid[Vector[String]];System.out.println("""res11: scalaz.Monoid[Vector[String]] = """ + $show(res$11));$skip(319); 
  
  //Vector version of GCD
  def gcd2(a: Int, b: Int): Writer[Vector[String], Int] =
    if (b == 0) for {
        _ <- Vector("Finished with " + a.shows).tell
      } yield a
    else for {
      result <- gcd2(b, a % b)
      _ <- Vector(a.shows + " mod " + b.shows + " = " + (a % b).shows).tell
    } yield result;System.out.println("""gcd2: (a: Int, b: Int)scalaz.Writer[Vector[String],Int]""");$skip(16); val res$12 = 
  
  gcd2(8, 3);System.out.println("""res12: scalaz.Writer[Vector[String],Int] = """ + $show(res$12));$skip(605); 
  
  
  // Speed tests between Vector and Lists in Writers:
  def vectorFinalCountDown(x: Int): Writer[Vector[String], Unit] = {
    import annotation.tailrec
    @tailrec def doFinalCountDown(x: Int, w: Writer[Vector[String], Unit]): Writer[Vector[String], Unit] = x match {
      case 0 => w >>= { _ => Vector("0").tell }
      case x => doFinalCountDown(x - 1, w >>= { _ => Vector(x.shows).tell })
    }
    
    val t0 = System.currentTimeMillis
    val r = doFinalCountDown(x, Vector[String]().tell)
    val t1 = System.currentTimeMillis
    r >>= { _ => Vector((t1 - t0).shows + " msec").tell }
  };System.out.println("""vectorFinalCountDown: (x: Int)scalaz.Writer[Vector[String],Unit]""");$skip(527); 
  
  def listFinalCountDown(x: Int): Writer[List[String], Unit] = {
    import annotation.tailrec
    @tailrec def doFinalCountDown(x: Int, w: Writer[List[String], Unit]): Writer[List[String], Unit] = x match {
      case 0 => w >>= { _ => List("0").tell }
      case x => doFinalCountDown(x - 1, w >>= { _ => List(x.shows).tell })
    }
    val t0 = System.currentTimeMillis
    val r = doFinalCountDown(x, List[String]().tell)
    val t1 = System.currentTimeMillis
    r >>= { _ => List((t1 - t0).shows + " msec").tell }
  };System.out.println("""listFinalCountDown: (x: Int)scalaz.Writer[List[String],Unit]""");$skip(43); 

  val x = vectorFinalCountDown(10000).run;System.out.println("""x  : scalaz.Id.Id[(Vector[String], Unit)] = """ + $show(x ));$skip(12); val res$13 = 
  x._1.last;System.out.println("""res13: String = """ + $show(res$13));$skip(43); 
  
  val y = listFinalCountDown(10000).run;System.out.println("""y  : scalaz.Id.Id[(List[String], Unit)] = """ + $show(y ));$skip(12); val res$14 = 
  y._1.last;System.out.println("""res14: String = """ + $show(res$14))}
}
