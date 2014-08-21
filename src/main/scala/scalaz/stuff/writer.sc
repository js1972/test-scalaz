package scalaz.stuff

import scalaz._
import Scalaz._

object writer {

  def isBigGang(x: Int): (Boolean, String) =
    (x > 9, "Compared gang size to 9.")           //> isBigGang: (x: Int)(Boolean, String)
    
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
  }
  
  isBigGang(10)                                   //> res0: (Boolean, String) = (true,Compared gang size to 9.)
  (3, "Smallish gang.") applyLog isBigGang        //> res1: (Boolean, String) = (false,Smallish gang.Compared gang size to 9.)
  3.set("Smallish gang.")                         //> res2: scalaz.Writer[String,Int] = WriterT((Smallish gang.,3))
  
  // these methods are injected to all types (by Scalaz._) and can be
  // used to create Writers
  // SET and TELL create Writers!
  3.set("something")                              //> res3: scalaz.Writer[String,Int] = WriterT((something,3))
  "something".tell                                //> res4: scalaz.Writer[String,Unit] = WriterT((something,()))
  
  // Helper to create a WriterT Monad - replaces MonadWriter
  MonadTell[Writer, String]                       //> res5: scalaz.MonadTell[scalaz.Writer,String] = scalaz.WriterTInstances$$ano
                                                  //| n$1@4f9fe278
  MonadTell[Writer, String].point(3).run          //> res6: scalaz.Id.Id[(String, Int)] = ("",3)
  
  
  
  // example of using for syntax with Writer
  def logNumber(x: Int): Writer[List[String], Int] =
    x.set(List("got number: " + x.shows))         //> logNumber: (x: Int)scalaz.Writer[List[String],Int]
  
  logNumber(3)                                    //> res7: scalaz.Writer[List[String],Int] = WriterT((List(got number: 3),3))
  logNumber(5)                                    //> res8: scalaz.Writer[List[String],Int] = WriterT((List(got number: 5),5))
  
  def multWithLog: Writer[List[String], Int] = for {
    a <- logNumber(3)
    b <- logNumber(5)
  } yield a * b                                   //> multWithLog: => scalaz.Writer[List[String],Int]
  
  multWithLog run                                 //> res9: scalaz.Id.Id[(List[String], Int)] = (List(got number: 3, got number: 
                                                  //| 5),15)
  
  
  // *** >>= (flatMap) is how you append to a Writer ***
  
  // GCD example
  // Each time .tell is called on the List[String] the Writer
  // is logging the value (List[String]).
  def gcd(a: Int, b: Int): Writer[List[String], Int] =
    if (b == 0) for {
        _ <- List("Finished with " + a.shows).tell
      } yield a
    else
      List(a.shows + " mod " + b.shows + " = " + (a % b).shows).tell >>= { _ => gcd(b, a % b) }
                                                  //> gcd: (a: Int, b: Int)scalaz.Writer[List[String],Int]
  
  gcd(8, 3).run                                   //> res10: scalaz.Id.Id[(List[String], Int)] = (List(8 mod 3 = 2, 3 mod 2 = 1, 
                                                  //| 2 mod 1 = 0, Finished with 1),1)
  
  // Lists can be slow: see table of performance:
  // http://docs.scala-lang.org/overviews/collections/performance-characteristics.html
  // Speed tests further below...
  
  Monoid[Vector[String]]                          //> res11: scalaz.Monoid[Vector[String]] = scalaz.std.IndexedSeqSubInstances$$a
                                                  //| non$4@6012dc6
  
  //Vector version of GCD
  def gcd2(a: Int, b: Int): Writer[Vector[String], Int] =
    if (b == 0) for {
        _ <- Vector("Finished with " + a.shows).tell
      } yield a
    else for {
      result <- gcd2(b, a % b)
      _ <- Vector(a.shows + " mod " + b.shows + " = " + (a % b).shows).tell
    } yield result                                //> gcd2: (a: Int, b: Int)scalaz.Writer[Vector[String],Int]
  
  gcd2(8, 3)                                      //> res12: scalaz.Writer[Vector[String],Int] = WriterT((Vector(Finished with 1,
                                                  //|  2 mod 1 = 0, 3 mod 2 = 1, 8 mod 3 = 2),1))
  
  
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
  }                                               //> vectorFinalCountDown: (x: Int)scalaz.Writer[Vector[String],Unit]
  
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
  }                                               //> listFinalCountDown: (x: Int)scalaz.Writer[List[String],Unit]

  val x = vectorFinalCountDown(10000).run         //> x  : scalaz.Id.Id[(Vector[String], Unit)] = (Vector(10000, 9999, 9998, 9997
                                                  //| , 9996, 9995, 9994, 9993, 9992, 9991, 9990, 9989, 9988, 9987, 9986, 9985, 9
                                                  //| 984, 9983, 9982, 9981, 9980, 9979, 9978, 9977, 9976, 9975, 9974, 9973, 9972
                                                  //| , 9971, 9970, 9969, 9968, 9967, 9966, 9965, 9964, 9963, 9962, 9961, 9960, 9
                                                  //| 959, 9958, 9957, 9956, 9955, 9954, 9953, 9952, 9951, 9950, 9949, 9948, 9947
                                                  //| , 9946, 9945, 9944, 9943, 9942, 9941, 9940, 9939, 9938, 9937, 9936, 9935, 9
                                                  //| 934, 9933, 9932, 9931, 9930, 9929, 9928, 9927, 9926, 9925, 9924, 9923, 9922
                                                  //| , 9921, 9920, 9919, 9918, 9917, 9916, 9915, 9914, 9913, 9912, 9911, 9910, 9
                                                  //| 909, 9908, 9907, 9906, 9905, 9904, 9903, 9902, 9901, 9900, 9899, 9898, 9897
                                                  //| , 9896, 9895, 9894, 9893, 9892, 9891, 9890, 9889, 9888, 9887, 9886, 9885, 9
                                                  //| 884, 9883, 9882, 9881, 9880, 9879, 9878, 9877, 9876, 9875, 9874, 9873, 9872
                                                  //| , 9871, 9870, 9869, 9868, 9867, 9866, 9865, 9864, 9863, 9862, 9861, 9860, 9
                                                  //| 859, 9858, 9857, 9856, 
                                                  //| Output exceeds cutoff limit.
  x._1.last                                       //> res13: String = 165 msec
  
  val y = listFinalCountDown(10000).run           //> y  : scalaz.Id.Id[(List[String], Unit)] = (List(10000, 9999, 9998, 9997, 99
                                                  //| 96, 9995, 9994, 9993, 9992, 9991, 9990, 9989, 9988, 9987, 9986, 9985, 9984,
                                                  //|  9983, 9982, 9981, 9980, 9979, 9978, 9977, 9976, 9975, 9974, 9973, 9972, 99
                                                  //| 71, 9970, 9969, 9968, 9967, 9966, 9965, 9964, 9963, 9962, 9961, 9960, 9959,
                                                  //|  9958, 9957, 9956, 9955, 9954, 9953, 9952, 9951, 9950, 9949, 9948, 9947, 99
                                                  //| 46, 9945, 9944, 9943, 9942, 9941, 9940, 9939, 9938, 9937, 9936, 9935, 9934,
                                                  //|  9933, 9932, 9931, 9930, 9929, 9928, 9927, 9926, 9925, 9924, 9923, 9922, 99
                                                  //| 21, 9920, 9919, 9918, 9917, 9916, 9915, 9914, 9913, 9912, 9911, 9910, 9909,
                                                  //|  9908, 9907, 9906, 9905, 9904, 9903, 9902, 9901, 9900, 9899, 9898, 9897, 98
                                                  //| 96, 9895, 9894, 9893, 9892, 9891, 9890, 9889, 9888, 9887, 9886, 9885, 9884,
                                                  //|  9883, 9882, 9881, 9880, 9879, 9878, 9877, 9876, 9875, 9874, 9873, 9872, 98
                                                  //| 71, 9870, 9869, 9868, 9867, 9866, 9865, 9864, 9863, 9862, 9861, 9860, 9859,
                                                  //|  9858, 9857, 9856, 9855
                                                  //| Output exceeds cutoff limit.
  y._1.last                                       //> res14: String = 589 msec
}