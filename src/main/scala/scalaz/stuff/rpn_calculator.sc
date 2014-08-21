package scalaz.stuff

import scalaz._
import Scalaz._

object rpn_calculator {
  def foldingFunction(list: List[Double], next: String): List[Double] = (list, next) match {
    case (x :: y :: ys, "*") => (y * x) :: ys
    case (x :: y :: ys, "+") => (y + x) :: ys
    case (x :: y :: ys, "-") => (y - x) :: ys
    case (xs, numString) => numString.toInt :: xs
  }                                               //> foldingFunction: (list: List[Double], next: String)List[Double]
  
  def solveRPN(s: String): Double =
    (s.split(' ').toList.foldLeft(Nil: List[Double]) { foldingFunction }).head
                                                  //> solveRPN: (s: String)Double
  
  
  solveRPN("10 4 3 + 2 * -")                      //> res0: Double = -4.0
  
  
  
  "1".parseInt                                    //> res1: scalaz.Validation[NumberFormatException,Int] = Success(1)
  "1".parseInt.toOption                           //> res2: Option[Int] = Some(1)
  "foo".parseInt                                  //> res3: scalaz.Validation[NumberFormatException,Int] = Failure(java.lang.Numbe
                                                  //| rFormatException: For input string: "foo")
  "foo".parseInt.toOption                         //> res4: Option[Int] = None
  
  
  // Updated folding function catering for errors:
  def foldingFunction2(list: List[Double], next: String): Option[List[Double]] = (list, next) match {
    case (x :: y :: ys, "*") => ((y * x) :: ys).point[Option]
    case (x :: y :: ys, "+") => ((y + x) :: ys).point[Option]
    case (x :: y :: ys, "-") => ((y - x) :: ys).point[Option]
    case (xs, numString) => numString.parseInt.toOption map {_ :: xs}
  }                                               //> foldingFunction2: (list: List[Double], next: String)Option[List[Double]]
  
  def solveRPN2(s: String): Option[Double] = for {
    List(x) <- s.split(' ').toList.foldLeftM(Nil: List[Double]) { foldingFunction2 }
  } yield x                                       //> solveRPN2: (s: String)Option[Double]
  
  
  solveRPN2("1 2 * 4 + ")                         //> res5: Option[Double] = Some(6.0)
  solveRPN2("1 2 * 4")                            //> res6: Option[Double] = None
  solveRPN("1 8 garbage")                         //> java.lang.NumberFormatException: For input string: "garbage"
                                                  //| 	at java.lang.NumberFormatException.forInputString(NumberFormatException.
                                                  //| java:65)
                                                  //| 	at java.lang.Integer.parseInt(Integer.java:495)
                                                  //| 	at java.lang.Integer.parseInt(Integer.java:530)
                                                  //| 	at scala.collection.immutable.StringLike$class.toInt(StringLike.scala:24
                                                  //| 1)
                                                  //| 	at scala.collection.immutable.StringOps.toInt(StringOps.scala:30)
                                                  //| 	at scalaz.stuff.rpn_calculator$$anonfun$main$1.scalaz$stuff$rpn_calculat
                                                  //| or$$anonfun$$foldingFunction$1(scalaz.stuff.rpn_calculator.scala:11)
                                                  //| 	at scalaz.stuff.rpn_calculator$$anonfun$main$1$$anonfun$solveRPN$1$1.app
                                                  //| ly(scalaz.stuff.rpn_calculator.scala:15)
                                                  //| 	at scalaz.stuff.rpn_calculator$$anonfun$main$1$$anonfun$solveRPN$1$1.app
                                                  //| ly(scalaz.stuff.rpn_calculator.scala:15)
                                                  //| 	at scala.collection.LinearSeqOptimized$class.foldLeft(LinearSeqOptimized
                                                  //| .scala:110)
                                                  //| 	at scala.collection.immutable.List.foldLeft(List.scala:83)
                                                  //| Output exceeds cutoff limit.
}