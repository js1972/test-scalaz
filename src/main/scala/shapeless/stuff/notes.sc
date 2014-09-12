package shapeless.stuff

import scalaz._
import Scalaz._
import shapeless._
  
object notes {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  // choose is a function from Sets to Options with no type specific cases
  object choose extends (Set ~> Option) {
    def apply[T](s : Set[T]) = s.headOption
  }
  
  choose(Set(1, 2, 3))                            //> res0: Option[Int] = Some(1)
  choose(Set('a', 'b', 'c'))                      //> res1: Option[Char] = Some(a)
  
  
  def pairApply(f: Set ~> Option) = (f(Set(1, 2, 3)), f(Set('a', 'b', 'c')))
                                                  //> pairApply: (f: scalaz.~>[Set,Option])(Option[Int], Option[Char])
  pairApply(choose)                               //> res2: (Option[Int], Option[Char]) = (Some(1),Some(a))
  
  
  // choose is convertible to an ordinary monomorphic function value and can be
  // mapped across an ordinary Scala List
  List(Set(1, 3, 5), Set(2, 4, 6)) map choose     //> res3: List[Option[Int]] = List(Some(1), Some(2))
  
  
  
  // size is a function from Ints or Strings or pairs to a 'size' defined
  // by type specific cases
  object size extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseString = at[String](_.length)
    implicit def caseTuple[T, U](implicit st : Case.Aux[T, Int], su : Case.Aux[U, Int]) =
      at[(T, U)](t => size(t._1)+size(t._2))
  }
  
  size(23)                                        //> res4: Int = 1
  size("foo")                                     //> res5: Int = 3
  size((23, "foo"))                               //> res6: Int = 4
  size(((23, "foo"), 13))                         //> res7: Int = 5
}