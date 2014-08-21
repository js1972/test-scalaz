package scalaz.stuff

import scalaz._
import Scalaz._

object kleisli {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(247); 
  // A Kleisli is a wrapper for a function of type A => M[B]
  // >=> is an alias for andThen
  // <=< is an alias for compose
 
  val f = Kleisli { (x: Int) => (x + 1).some };System.out.println("""f  : scalaz.Kleisli[Option,Int,Int] = """ + $show(f ));$skip(49); 
  val g = Kleisli { (x: Int) => (x * 100).some };System.out.println("""g  : scalaz.Kleisli[Option,Int,Int] = """ + $show(g ));$skip(72); val res$0 = 
  
  // 4.some flatMap (f compose g) - rhs first
  4.some >>= (f <=< g);System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(38); val res$1 = 
  // lhs first
  4.some >>= (f >=> g);System.out.println("""res1: Option[Int] = """ + $show(res$1));$skip(130); 
  
  
  val addStuff: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b;System.out.println("""addStuff  : scalaz.Reader[Int,Int] = """ + $show(addStuff ));$skip(17); val res$2 = 
  
  addStuff(3);System.out.println("""res2: scalaz.Id.Id[Int] = """ + $show(res$2))}
}
