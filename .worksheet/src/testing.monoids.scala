package testing

import scalaz._
import Scalaz._

object monoids {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(268); 
 
 //Sum a list of any Monoid...
  def sum[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  };System.out.println("""sum: [M[_], A](xs: M[A])(implicit evidence$3: testing.FoldLeft[M], implicit evidence$4: testing.Monoid[A])A""");$skip(27); val res$0 = 
  
  sum(List(1, 2, 3, 4));System.out.println("""res0: Int = """ + $show(res$0));$skip(27); val res$1 = 
  sum(List("a", "b", "c"));System.out.println("""res1: String = """ + $show(res$1));$skip(136); 
 
 
  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  };System.out.println("""toMonoidOp: [A](a: A)(implicit evidence$5: testing.Monoid[A])testing.MonoidOp[A]""");$skip(10); val res$2 = 
  3 |+| 4;System.out.println("""res2: Int = """ + $show(res$2));$skip(22); val res$3 = 
  "a" |+| "b" |+| "c";System.out.println("""res3: String = """ + $show(res$3));$skip(18); val res$4 = 
  "one" |+| "two";System.out.println("""res4: String = """ + $show(res$4));$skip(34); val res$5 = 

  
  //ScalaZ stuff
  1.some | 2;System.out.println("""res5: Int = """ + $show(res$5));$skip(23); val res$6 = 
  Some(1).getOrElse(2);System.out.println("""res6: Int = """ + $show(res$6));$skip(22); val res$7 = 
  
  (1 > 10)? 1 | 2;System.out.println("""res7: Int = """ + $show(res$7));$skip(23); val res$8 = 
  if (1 > 10) 1 else 2;System.out.println("""res8: Int = """ + $show(res$8));$skip(51); val res$9 = 
  
  
  
  (none: Option[String]) |+| "jason".some;System.out.println("""res9: Option[String] = """ + $show(res$9));$skip(40); val res$10 = 
  (Ordering.LT: Ordering).some |+| none;System.out.println("""res10: Option[scalaz.Ordering] = """ + $show(res$10))}
}
