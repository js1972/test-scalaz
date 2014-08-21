package scalaz.stuff

import scalaz._
import Scalaz._


object enum {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(82); val res$0 = 
  'a' to 'e';System.out.println("""res0: scala.collection.immutable.NumericRange.Inclusive[Char] = """ + $show(res$0));$skip(14); val res$1 = 
  'a' |-> 'e';System.out.println("""res1: List[Char] = """ + $show(res$1));$skip(10); val res$2 = 
  3 |=> 5;System.out.println("""res2: scalaz.EphemeralStream[Int] = """ + $show(res$2));$skip(11); val res$3 = 
  'B'.succ;System.out.println("""res3: Char = """ + $show(res$3));$skip(35); val res$4 = 
  
  
  implicitly[Enum[Char]].min;System.out.println("""res4: Option[Char] = """ + $show(res$4));$skip(29); val res$5 = 
  implicitly[Enum[Char]].max;System.out.println("""res5: Option[Char] = """ + $show(res$5));$skip(28); val res$6 = 
  implicitly[Enum[Int]].min;System.out.println("""res6: Option[Int] = """ + $show(res$6));$skip(28); val res$7 = 
  implicitly[Enum[Int]].max;System.out.println("""res7: Option[Int] = """ + $show(res$7))}
  
}
