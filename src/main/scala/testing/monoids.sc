package testing

import scalaz._
import Scalaz._

object monoids {
 
 //Sum a list of any Monoid...
  def sum[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }                                               //> sum: [M[_], A](xs: M[A])(implicit evidence$3: testing.FoldLeft[M], implicit 
                                                  //| evidence$4: testing.Monoid[A])A
  
  sum(List(1, 2, 3, 4))                           //> res0: Int = 10
  sum(List("a", "b", "c"))                        //> res1: String = abc
 
 
  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  }                                               //> toMonoidOp: [A](a: A)(implicit evidence$5: testing.Monoid[A])testing.MonoidO
                                                  //| p[A]
  3 |+| 4                                         //> res2: Int = 7
  "a" |+| "b" |+| "c"                             //> res3: String = abc
  "one" |+| "two"                                 //> res4: String = onetwo

  
  //ScalaZ stuff
  1.some | 2                                      //> res5: Int = 1
  Some(1).getOrElse(2)                            //> res6: Int = 1
  
  (1 > 10)? 1 | 2                                 //> res7: Int = 2
  if (1 > 10) 1 else 2                            //> res8: Int = 2
  
  
  
  (none: Option[String]) |+| "jason".some         //> res9: Option[String] = Some(jason)
  (Ordering.LT: Ordering).some |+| none           //> res10: Option[scalaz.Ordering] = Some(LT)
}