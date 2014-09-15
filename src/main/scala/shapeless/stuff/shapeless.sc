package shapeless.stuff

import scalaz._
import Scalaz._
import shapeless._
//import poly._

  
object shapeless {

  // First lets look at scalaz natural transformation
  val toList = new (Option ~> List) {
    def apply[T](opt: Option[T]): List[T] =
      opt.toList
  }
  toList(3.some)
  toList(true.some)
  
  
  // Shapeless now... Polymorphic function values
  
  // choose is a function from 'Sets to Options' with no type specific cases
  object choose extends (Set ~> Option) {
    def apply[T](s: Set[T]) = s.headOption
  }

  choose(Set(1, 2, 3))
  choose(Set('a', 'b', 'c'))
  
  
  def pairApply(f: Set ~> Option) = (f(Set(1, 2, 3)), f(Set('a', 'b', 'c')))
  
  pairApply(choose)
  
  // choose is convertible to an ordinary monomorphic function value and can be
  // mapped across an ordinary Scala List
  List(Set(1, 3, 5), Set(2, 4, 6)) map choose
 
 
  // size is a function from Ints or Strings or pairs to a 'size' defined
  // by type specific cases
  import language.existentials
  import language.experimental.macros
  import reflect.macros.whitebox
  
  object size extends Poly {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseString = at[String](_.length)
    implicit def caseTuple[T, U](implicit st : Case.Aux[T, Int], su : Case.Aux[U, Int]) =
      at[(T, U)](t => size(t._1)+size(t._2))
  }
}