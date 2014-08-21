package scalaz.stuff

import scalaz._
import Scalaz._


object orderdingtest {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(132); val res$0 = 
  (Ordering.LT: Ordering) |+| (Ordering.GT: Ordering);System.out.println("""res0: scalaz.Ordering = """ + $show(res$0));$skip(111); 
  
  def lengthCompare(lhs: String, rhs: String): Ordering =
    (lhs.length ?|? rhs.length) |+| (lhs ?|? rhs);System.out.println("""lengthCompare: (lhs: String, rhs: String)scalaz.Ordering""");$skip(34); val res$1 = 
  
  lengthCompare("zen", "ants");System.out.println("""res1: scalaz.Ordering = """ + $show(res$1));$skip(30); val res$2 = 
  lengthCompare("zen", "ant");System.out.println("""res2: scalaz.Ordering = """ + $show(res$2));$skip(21); val res$3 = 
  
  "abc" ?|? "xyz";System.out.println("""res3: scalaz.Ordering = """ + $show(res$3))}
}
