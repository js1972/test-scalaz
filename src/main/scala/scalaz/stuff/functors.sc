package scalaz.stuff

import scalaz._
import Scalaz._


object functors {
  List(1, 2, 3) map {_ + 1}                       //> res0: List[Int] = List(2, 3, 4)
  (1, 2, 3, 4, 5, 6) map {_ + 1}                  //> res1: (Int, Int, Int, Int, Int, Int) = (1,2,3,4,5,7)
  (1, 2, 3, 4, 5, 6) map {_ + 1}                  //> res2: (Int, Int, Int, Int, Int, Int) = (1,2,3,4,5,7)
  
  (1, 2) map {1 + _}                              //> res3: (Int, Int) = (1,3)
  
  (1, 2)._2                                       //> res4: Int = 2
  val x = (4, 3)                                  //> x  : (Int, Int) = (4,3)
  x.map(1 +)                                      //> res5: (Int, Int) = (4,4)
  
  ("a" -> 2).map(_ + 1)                           //> res6: (String, Int) = (a,3)
  
  
  //Functor for Funtion1
  val f = ((x: Int) => x + 1) map {_ * 7}         //> f  : Int => Int = <function1>
  f(3)                                            //> res7: Int = 28
  
  ((_: Int) * 3)(3)                               //> res8: Int = 9
  (((_: Int) * 3) map {_ + 100}) (3)              //> res9: Int = 109
  
  List(1, 2, 3) map {_ * 3}                       //> res10: List[Int] = List(3, 6, 9)
  List(1, 2, 3) map {3*}                          //> res11: List[Int] = List(3, 6, 9)
  
  val l = Functor[List].lift {(_: Int) * 2}       //> l  : List[Int] => List[Int] = <function1>
  l(List(3))                                      //> res12: List[Int] = List(6)
  
  List(1, 2, 3) >| "x"                            //> res13: List[String] = List(x, x, x)
  List(1, 2, 3) as "x"                            //> res14: List[String] = List(x, x, x)
  List(1, 2, 3).fpair                             //> res15: List[(Int, Int)] = List((1,1), (2,2), (3,3))
  List(1, 2, 3).strengthL("x")                    //> res16: List[(String, Int)] = List((x,1), (x,2), (x,3))
  List(1, 2, 3).strengthR("x")                    //> res17: List[(Int, String)] = List((1,x), (2,x), (3,x))
  List(1, 2, 3).void                              //> res18: List[Unit] = List((), (), ())
  
  
  
  // This is some monoid apply's ->
  1.some |+| 1.some                               //> res19: Option[Int] = Some(2)
  (1, 1, "a") |+| (3, 3, "a")                     //> res20: (Int, Int, String) = (4,4,aa)
  
  
  //functor law
  List(1, 2, 3) map {identity} assert_=== List(1, 2, 3)
  (List(1, 2, 3) map {{(_: Int) * 3} map {(_: Int) + 1}}) assert_=== (List(1, 2, 3) map {(_: Int) * 3} map {(_: Int) + 1})
  
  
}