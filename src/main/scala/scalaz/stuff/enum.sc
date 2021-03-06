package scalaz.stuff

import scalaz._
import Scalaz._


object enum {
  'a' to 'e'                                      //> res0: scala.collection.immutable.NumericRange.Inclusive[Char] = NumericRange(
                                                  //| a, b, c, d, e)
  'a' |-> 'e'                                     //> res1: List[Char] = List(a, b, c, d, e)
  3 |=> 5                                         //> res2: scalaz.EphemeralStream[Int] = scalaz.EphemeralStreamFunctions$$anon$4@
                                                  //| 5c7b24c2
  'B'.succ                                        //> res3: Char = C
  
  
  implicitly[Enum[Char]].min                      //> res4: Option[Char] = Some( )
  implicitly[Enum[Char]].max                      //> res5: Option[Char] = Some(?)
  implicitly[Enum[Int]].min                       //> res6: Option[Int] = Some(-2147483648)
  implicitly[Enum[Int]].max                       //> res7: Option[Int] = Some(2147483647)
  
}