package shapeless.stuff

import scalaz._
import Scalaz._
import shapeless._
//import poly._

  
object shapeless {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(272); 

  // First lets look at scalaz natural transformation
  val toList = new (Option ~> List) {
    def apply[T](opt: Option[T]): List[T] =
      opt.toList
  };System.out.println("""toList  : scalaz.~>[Option,List] = """ + $show(toList ));$skip(17); val res$0 = 
  toList(3.some);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(20); val res$1 = 
  toList(true.some)
  
  
  // Shapeless now... Polymorphic function values
  
  // choose is a function from 'Sets to Options' with no type specific cases
  object choose extends (Set ~> Option) {
    def apply[T](s: Set[T]) = s.headOption
  };System.out.println("""res1: List[Boolean] = """ + $show(res$1));$skip(250); val res$2 = 

  choose(Set(1, 2, 3));System.out.println("""res2: Option[Int] = """ + $show(res$2));$skip(29); val res$3 = 
  choose(Set('a', 'b', 'c'));System.out.println("""res3: Option[Char] = """ + $show(res$3));$skip(83); 
  
  
  def pairApply(f: Set ~> Option) = (f(Set(1, 2, 3)), f(Set('a', 'b', 'c')));System.out.println("""pairApply: (f: scalaz.~>[Set,Option])(Option[Int], Option[Char])""");$skip(23); val res$4 = 
  
  pairApply(choose);System.out.println("""res4: (Option[Int], Option[Char]) = """ + $show(res$4));$skip(171); val res$5 = 
  
  // choose is convertible to an ordinary monomorphic function value and can be
  // mapped across an ordinary Scala List
  List(Set(1, 3, 5), Set(2, 4, 6)) map choose;System.out.println("""res5: List[Option[Int]] = """ + $show(res$5))}
 
 
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
