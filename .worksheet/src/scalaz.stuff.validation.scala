package scalaz.stuff

import scalaz._
import Scalaz._

object validation {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(162); val res$0 = 
  // these methods are added to all data types by scalaz
  "event 1 ok".success[String];System.out.println("""res0: scalaz.Validation[String,String] = """ + $show(res$0));$skip(36); val res$1 = 
  "event 1 failed!".failure[String];System.out.println("""res1: scalaz.Validation[String,String] = """ + $show(res$1));$skip(185); val res$2 = 
  
  // Validation IS NOT A MONAD - Just an APPLICATIVE FUNCTOR
  ("event 1 ok".success[String] |@| "event 2 failed!".failure[String] |@| "event 3 failed!".failure[String]) {_ + _ + _};System.out.println("""res2: scalaz.Validation[String,String] = """ + $show(res$2));$skip(447); val res$3 = 
  
  // Validation keeps going and reports all the failures,
  // whereas the \/ monad cuts the calulation short at the
  // first failure.
  
  // However, above, the error messages are joined together into a mess,
  // would be better as a List, which is where NEL (Non empty list) comes in.
  // A non-empty list is guaranteed to have t least one value, so that head always works.
  // Scalaz's IdOps add wrapNel tpo all data types
  1.wrapNel;System.out.println("""res3: scalaz.NonEmptyList[Int] = """ + $show(res$3));$skip(37); val res$4 = 
  
  "event 1 ok".successNel[String];System.out.println("""res4: scalaz.ValidationNel[String,String] = """ + $show(res$4));$skip(39); val res$5 = 
  "event 1 failed!".failureNel[String];System.out.println("""res5: scalaz.ValidationNel[String,String] = """ + $show(res$5));$skip(133); val res$6 = 
  
  ("event 1 ok".successNel[String] |@| "event 2 failed!".failureNel[String] |@| "event 3 failed!".failureNel[String]) {_ + _ + _};System.out.println("""res6: scalaz.Validation[scalaz.NonEmptyList[String],String] = """ + $show(res$6))}
}
