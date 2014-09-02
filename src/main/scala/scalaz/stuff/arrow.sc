package scalaz.stuff

import scalaz._
import Scalaz._

object arrow {

  // An arrow is the term used in category theory as an abstract notation of things that behave like a function.
  // For functions >>> is andThen, <<< is compose.
  // Arrows cna be useful when you need to add some context to functions or pairs.
  
  val f = (_:Int) + 1                             //> f  : Int => Int = <function1>
  val g = (_:Int) * 100                           //> g  : Int => Int = <function1>
  
  (f >>> g)(2)                                    //> res0: Int = 300
  (f <<< g)(2)                                    //> res1: Int = 201
  
  // *** runs two arrows on a pair of values:
  (f *** g)(1, 2)                                 //> res2: (Int, Int) = (2,200)
  
  // &&& runs two arrows on the same value:
  (f &&& g)(2)                                    //> res3: (Int, Int) = (3,200)
}