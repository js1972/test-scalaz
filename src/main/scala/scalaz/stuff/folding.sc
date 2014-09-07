package scalaz.stuff

import scalaz._
import Scalaz._
import Tags._


/**
 * Also see taggedtypes.sc.
 */
object folding {
  List(1, 2, 3).foldRight (1) {_ * _}             //> res0: Int = 6
  9.some.foldLeft(2) {_ + _}                      //> res1: Int = 11
  
  
  List(1, 2, 3) foldMap { identity }              //> res2: Int = 6
  Tags.Disjunction                                //> res3: scalaz.Tag.TagOf[scalaz.Tags.Disjunction] = scalaz.Tag$TagOf@5010ad7c
  Tags.Disjunction(true) |+| Tags.Disjunction(false)
                                                  //> res4: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  Tags.MinVal.unwrap(Tags.MinVal(1) |+| Tags.MinVal(2))
                                                  //> res5: Int = 1
  Tags.Disjunction.unwrap(Tags.Disjunction(true)) //> res6: Boolean = true
  
  
  
  // Lets use the tagged type for multiplication to fold over a list.
  // Whats happening here is that foldMap needs a function that takes an Int
  // and returns a Monoid - In this case we are returning the Int tagged as
  // the type Int @@ Multiplication. Scalaz has an implicit instance to turn
  // this into a Monoid with multiplication as the mappend operation.
  
  Tags.Multiplication                             //> res7: scalaz.Tag.TagOf[scalaz.Tags.Multiplication] = scalaz.Tag$TagOf@3a2cf8
                                                  //| 5b
  Tags.Multiplication(10)                         //> res8: scalaz.@@[Int,scalaz.Tags.Multiplication] = 10
  List(1, 2, 3) foldMap { Tags.Multiplication.apply }
                                                  //> res9: scalaz.@@[Int,scalaz.Tags.Multiplication] = 6
 
  
  
  // Lets use foldMap again but with a boolean operation (Disjunction which is OR).
  
  List(true, false, true, true) foldMap { Tags.Disjunction.apply }
                                                  //> res10: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  
  
  // Note that the apply method is called every time in the list which is a waste.
  // Instead we can use Tags.Disjunction.subst to convert the List of Ints to a
  // List of tagged Ints instead (Int @@ Tags.Disjunction). We can then just use
  // fold over the tagged types as per normal.
  // Or further below I've shown how we can still use foldMap with the identity function.
  
  val l = Tags.Disjunction.subst(List(true, false, true, true))
                                                  //> l  : List[scalaz.@@[Boolean,scalaz.Tags.Disjunction]] = List(true, false, t
                                                  //| rue, true)
  l.fold(Disjunction(false)) { _ |+| _ }          //> res11: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  
  val x = l.head                                  //> x  : scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  val y: Boolean = Tags.Disjunction.unwrap(x)     //> y  : Boolean = true
  
  l foldMap { identity }                          //> res12: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  Tags.Disjunction.subst(List(false, false, false, false)) foldMap { identity }
                                                  //> res13: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = false
  
}