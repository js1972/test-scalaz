package scalaz.stuff

import scalaz._
import Scalaz._


object applicative {
  //map only wants a one-parameter function -> gives a type mismatch error below:
  //List(1, 2, 3, 4) map {(_: Int) * (_: Int)}
  
  //We have to curry the map function like this ->
  val l = List(1, 2, 3, 4) map {(_: Int) * (_: Int)}.curried
                                                  //> l  : List[Int => Int] = List(<function1>, <function1>, <function1>, <functio
                                                  //| n1>)
  l map {_(9)}                                    //> res0: List[Int] = List(9, 18, 27, 36)
  
  
  
  //Scalaz Applicative
  
  //using point (known as pure in Haskell)
  1.point[List]                                   //> res1: List[Int] = List(1)
  1.point[Option]                                 //> res2: Option[Int] = Some(1)
  1.point[Option] map {_ + 2}                     //> res3: Option[Int] = Some(3)
  1.point[List] map {_ + 2}                       //> res4: List[Int] = List(3)
  
  //apply <*> - Gets the function inside a Functor and maps it over another Functor
  //in this sample, Option is a Functor
  9.some <*> {(_: Int) + 3}.some                  //> res5: Option[Int] = Some(12)
  ((_: Int) + (_: Int)).curried.some              //> res6: Option[Int => (Int => Int)] = Some(<function1>)
  3.some <*> {9.some <*> {(_: Int) + (_: Int)}.curried.some}
                                                  //> res7: Option[Int] = Some(12)
  
  
  // ^ is a shortcut for apply2, ^^ for apply3, etc. allowing muiltiple containers to be applied.
  // though |@| seems to be preferred.
  ^(List(1, 2, 3), List(10, 100, 1000)) { _ * _ } //> res8: List[Int] = List(10, 100, 1000, 20, 200, 2000, 30, 300, 3000)
  
  
  //return the lhs (<*) or rhs (*>)
  1.some <* 2.some                                //> res9: Option[Int] = Some(1)
  none <* 2.some                                  //> res10: Option[Nothing] = None
  1.some *> 2.some                                //> res11: Option[Int] = Some(2)
  none *> 2.some                                  //> res12: Option[Int] = None
  
  
  //Applicative Builder |@| - Extracts values from containers and applies them to a function
  (3.some |@| 5.some) {_ + _}                     //> res13: Option[Int] = Some(8)
  
  //lists are applicative functors
  List(1, 2, 3) <*> List((_: Int) * 0, (_: Int) + 100, (x: Int) => x * x)
                                                  //> res14: List[Int] = List(0, 0, 0, 101, 102, 103, 1, 4, 9)
  
  (List("ha", "heh", "hmm") |@| List("?", "!", ".")) {_ + _}
                                                  //> res15: List[String] = List(ha?, ha!, ha., heh?, heh!, heh., hmm?, hmm!, hmm
                                                  //| .)
  
  // with functions
  val f = ({(_: Int) * 2} |@| {(_: Int) + 10}) { _ + _ }
                                                  //> f  : Int => Int = <function1>
  f(5)                                            //> res16: Int = 25
  
  
  
  // Monoidal applicatives
  Monoid[Int].applicative.ap2(1, 1)(0)            //> res17: Int = 2
  Monoid[List[Int]].applicative.ap2(List(1), List(1))(Nil)
                                                  //> res18: List[Int] = List(1, 1)
  
  // Combining applicative functors
  // Product of List and Option
  Applicative[List].product[Option]               //> res19: scalaz.Applicative[[?](List[?], Option[?])] = scalaz.Applicative$$an
                                                  //| on$2@40494e54
  Applicative[List].product[Option].point(1)      //> res20: (List[Int], Option[Int]) = (List(1),Some(1))
  
  // Product seems to be implemented as a Tuple2, lets append them:
  ((List(1), 1.some) |@| (List(1), 1.some)) { _ |+| _ }
                                                  //> res21: (List[Int], Option[Int]) = (List(1, 1),Some(2))
  
  
  // Let compose List and Otion
  Applicative[List].compose[Option]               //> res22: scalaz.Applicative[[?]List[Option[?]]] = scalaz.Applicative$$anon$1@
                                                  //| 1c6addb8
  Applicative[List].compose[Option].point(1)      //> res23: List[Option[Int]] = List(Some(1))
  
  // We can compose applicatives and they remain applicatives....
}