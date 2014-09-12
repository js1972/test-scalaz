package shapeless.stuff

import scalaz._
import Scalaz._
import shapeless._
  
object notes {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(137); 
  println("Welcome to the Scala worksheet")
  
  // choose is a function from Sets to Options with no type specific cases
  object choose extends (Set ~> Option) {
    def apply[T](s : Set[T]) = s.headOption
  };$skip(194); val res$0 = 
  
  choose(Set(1, 2, 3));System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(29); val res$1 = 
  choose(Set('a', 'b', 'c'));System.out.println("""res1: Option[Char] = """ + $show(res$1));$skip(83); 
  
  
  def pairApply(f: Set ~> Option) = (f(Set(1, 2, 3)), f(Set('a', 'b', 'c')));System.out.println("""pairApply: (f: scalaz.~>[Set,Option])(Option[Int], Option[Char])""");$skip(20); val res$2 = 
  pairApply(choose);System.out.println("""res2: (Option[Int], Option[Char]) = """ + $show(res$2));$skip(174); val res$3 = 
  
  
  // choose is convertible to an ordinary monomorphic function value and can be
  // mapped across an ordinary Scala List
  List(Set(1, 3, 5), Set(2, 4, 6)) map choose
  
  
  
  // size is a function from Ints or Strings or pairs to a 'size' defined
  // by type specific cases
  object size extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseString = at[String](_.length)
    implicit def caseTuple[T, U](implicit st : Case.Aux[T, Int], su : Case.Aux[U, Int]) =
      at[(T, U)](t => size(t._1)+size(t._2))
  };System.out.println("""res3: List[Option[Int]] = """ + $show(res$3));$skip(388); val res$4 = 
  
  size(23);System.out.println("""res4: Int = """ + $show(res$4));$skip(14); val res$5 = 
  size("foo");System.out.println("""res5: Int = """ + $show(res$5));$skip(20); val res$6 = 
  size((23, "foo"));System.out.println("""res6: Int = """ + $show(res$6));$skip(26); val res$7 = 
  size(((23, "foo"), 13));System.out.println("""res7: Int = """ + $show(res$7))}
}
