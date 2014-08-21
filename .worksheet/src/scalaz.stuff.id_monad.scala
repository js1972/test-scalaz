package scalaz.stuff

import scalaz._
import Scalaz._

object id_monad {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(85); val res$0 = 
  0: Id[Int];System.out.println("""res0: scalaz.Scalaz.Id[Int] = """ + $show(res$0));$skip(79); val res$1 = 
  
  // |> applies self to the given function
  1 + 2 + 3 |> { _.point[List] };System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(25); val res$2 = 
  1 + 2 + 3 |> { _ * 6 };System.out.println("""res2: Int = """ + $show(res$2));$skip(174); val res$3 = 
  
  // visit - if the given partial function is defined for self,
  // run this, otherwise lift self into F (which is List below).
  1 visit { case x@(2|3) => List(x * 2) };System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(42); val res$4 = 
  2 visit { case x@(2|3) => List(x * 2) };System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(30); val res$5 = 
  
  (1 > 10) ?? { List(1) };System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(27); val res$6 = 
  (22 > 10) ?? { List(1) };System.out.println("""res6: List[Int] = """ + $show(res$6))}
}
