package scalaz.stuff

import scalaz._
import Scalaz._

object arrow {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(342); 

  // An arrow is the term used in category theory as an abstract notation of things that behave like a function.
  // For functions >>> is andThen, <<< is compose.
  // Arrows cna be useful when you need to add some context to functions or pairs.
  
  val f = (_:Int) + 1;System.out.println("""f  : Int => Int = """ + $show(f ));$skip(24); 
  val g = (_:Int) * 100;System.out.println("""g  : Int => Int = """ + $show(g ));$skip(18); val res$0 = 
  
  (f >>> g)(2);System.out.println("""res0: Int = """ + $show(res$0));$skip(15); val res$1 = 
  (f <<< g)(2);System.out.println("""res1: Int = """ + $show(res$1));$skip(67); val res$2 = 
  
  // *** runs two arrows on a pair of values:
  (f *** g)(1, 2);System.out.println("""res2: (Int, Int) = """ + $show(res$2));$skip(62); val res$3 = 
  
  // &&& runs two arrows on the same value:
  (f &&& g)(2);System.out.println("""res3: (Int, Int) = """ + $show(res$3))}
}
