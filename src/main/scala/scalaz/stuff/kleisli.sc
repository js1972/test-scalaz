package scalaz.stuff

import scalaz._
import Scalaz._

object kleisli {
  // A Kleisli is a wrapper for a function of type A => M[B]
  // >=> is an alias for andThen
  // <=< is an alias for compose
 
  val f = Kleisli { (x: Int) => (x + 1).some }    //> f  : scalaz.Kleisli[Option,Int,Int] = Kleisli(<function1>)
  val g = Kleisli { (x: Int) => (x * 100).some }  //> g  : scalaz.Kleisli[Option,Int,Int] = Kleisli(<function1>)
  
  // 4.some flatMap (f compose g) - rhs first
  4.some >>= (f <=< g)                            //> res0: Option[Int] = Some(401)
  // lhs first
  4.some >>= (f >=> g)                            //> res1: Option[Int] = Some(500)
  
  
  val addStuff: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b                                   //> addStuff  : scalaz.Reader[Int,Int] = Kleisli(<function1>)
  
  addStuff(3)                                     //> res2: scalaz.Id.Id[Int] = 19
}