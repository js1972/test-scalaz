package scalaz.stuff

import scalaz._
import Scalaz._

object rpn_calculator {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(363); 
  def foldingFunction(list: List[Double], next: String): List[Double] = (list, next) match {
    case (x :: y :: ys, "*") => (y * x) :: ys
    case (x :: y :: ys, "+") => (y + x) :: ys
    case (x :: y :: ys, "-") => (y - x) :: ys
    case (xs, numString) => numString.toInt :: xs
  };System.out.println("""foldingFunction: (list: List[Double], next: String)List[Double]""");$skip(118); 
  
  def solveRPN(s: String): Double =
    (s.split(' ').toList.foldLeft(Nil: List[Double]) { foldingFunction }).head;System.out.println("""solveRPN: (s: String)Double""");$skip(35); val res$0 = 
  
  
  solveRPN("10 4 3 + 2 * -");System.out.println("""res0: Double = """ + $show(res$0));$skip(24); val res$1 = 
  
  
  
  "1".parseInt;System.out.println("""res1: scalaz.Validation[NumberFormatException,Int] = """ + $show(res$1));$skip(24); val res$2 = 
  "1".parseInt.toOption;System.out.println("""res2: Option[Int] = """ + $show(res$2));$skip(17); val res$3 = 
  "foo".parseInt;System.out.println("""res3: scalaz.Validation[NumberFormatException,Int] = """ + $show(res$3));$skip(26); val res$4 = 
  "foo".parseInt.toOption;System.out.println("""res4: Option[Int] = """ + $show(res$4));$skip(419); 
  
  
  // Updated folding function catering for errors:
  def foldingFunction2(list: List[Double], next: String): Option[List[Double]] = (list, next) match {
    case (x :: y :: ys, "*") => ((y * x) :: ys).point[Option]
    case (x :: y :: ys, "+") => ((y + x) :: ys).point[Option]
    case (x :: y :: ys, "-") => ((y - x) :: ys).point[Option]
    case (xs, numString) => numString.parseInt.toOption map {_ :: xs}
  };System.out.println("""foldingFunction2: (list: List[Double], next: String)Option[List[Double]]""");$skip(151); 
  
  def solveRPN2(s: String): Option[Double] = for {
    List(x) <- s.split(' ').toList.foldLeftM(Nil: List[Double]) { foldingFunction2 }
  } yield x;System.out.println("""solveRPN2: (s: String)Option[Double]""");$skip(32); val res$5 = 
  
  
  solveRPN2("1 2 * 4 + ");System.out.println("""res5: Option[Double] = """ + $show(res$5));$skip(23); val res$6 = 
  solveRPN2("1 2 * 4");System.out.println("""res6: Option[Double] = """ + $show(res$6));$skip(26); val res$7 = 
  solveRPN("1 8 garbage");System.out.println("""res7: Double = """ + $show(res$7))}
}
