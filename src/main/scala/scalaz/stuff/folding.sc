package scalaz.stuff

import scalaz._
import Scalaz._
import Tags._


object folding {
  List(1, 2, 3).foldRight (1) {_ * _}             //> res0: Int = 6
  9.some.foldLeft(2) {_ + _}                      //> res1: Int = 11
  
  
  List(1, 2, 3) foldMap { identity }              //> res2: Int = 6
  Tags.Disjunction                                //> res3: scalaz.Tag.TagOf[scalaz.Tags.Disjunction] = scalaz.Tag$TagOf@313179e3
  Tags.Disjunction(true) |+| Tags.Disjunction(false)
                                                  //> res4: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  Tags.MinVal.unwrap(Tags.MinVal(1) |+| Tags.MinVal(2))
                                                  //> res5: Int = 1
  Tags.Disjunction.unwrap(Tags.Disjunction(true)) //> res6: Boolean = true
  
  //(List(1, 2, 3) foldMap {Tags.Multiplication}: Int) assert_=== 6
  List(true, false, true, true) foldMap { Tags.Disjunction.apply }
                                                  //> res7: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
  
  val l = Tags.Disjunction.subst(List(true, false, true, true))
                                                  //> l  : List[scalaz.@@[Boolean,scalaz.Tags.Disjunction]] = List(true, false, tr
                                                  //| ue, true)
  //l foldMap { Tags.Disjunction }
  
  
}