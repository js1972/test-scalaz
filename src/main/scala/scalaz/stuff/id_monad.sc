package scalaz.stuff

import scalaz._
import Scalaz._

object id_monad {
  0: Id[Int]                                      //> res0: scalaz.Scalaz.Id[Int] = 0
  
  // |> applies self to the given function
  1 + 2 + 3 |> { _.point[List] }                  //> res1: List[Int] = List(6)
  1 + 2 + 3 |> { _ * 6 }                          //> res2: Int = 36
  
  // visit - if the given partial function is defined for self,
  // run this, otherwise lift self into F (which is List below).
  1 visit { case x@(2|3) => List(x * 2) }         //> res3: List[Int] = List(1)
  2 visit { case x@(2|3) => List(x * 2) }         //> res4: List[Int] = List(4)
  
  (1 > 10) ?? { List(1) }                         //> res5: List[Int] = List()
  (22 > 10) ?? { List(1) }                        //> res6: List[Int] = List(1)
}