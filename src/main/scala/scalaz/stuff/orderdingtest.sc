package scalaz.stuff

import scalaz._
import Scalaz._


object orderdingtest {
  (Ordering.LT: Ordering) |+| (Ordering.GT: Ordering)
                                                  //> res0: scalaz.Ordering = LT
  
  def lengthCompare(lhs: String, rhs: String): Ordering =
    (lhs.length ?|? rhs.length) |+| (lhs ?|? rhs) //> lengthCompare: (lhs: String, rhs: String)scalaz.Ordering
  
  lengthCompare("zen", "ants")                    //> res1: scalaz.Ordering = LT
  lengthCompare("zen", "ant")                     //> res2: scalaz.Ordering = GT
  
  "abc" ?|? "xyz"                                 //> res3: scalaz.Ordering = LT
}