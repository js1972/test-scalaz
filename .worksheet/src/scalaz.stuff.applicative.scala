package scalaz.stuff

import scalaz._
import Scalaz._


object applicative {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(320); 
  //map only wants a one-parameter function -> gives a type mismatch error below:
  //List(1, 2, 3, 4) map {(_: Int) * (_: Int)}
  
  //We have to curry the map function like this ->
  val l = List(1, 2, 3, 4) map {(_: Int) * (_: Int)}.curried;System.out.println("""l  : List[Int => Int] = """ + $show(l ));$skip(15); val res$0 = 
  l map {_(9)};System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(94); val res$1 = 
  
  
  
  //Scalaz Applicative
  
  //using point (known as pure in Haskell)
  1.point[List];System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(18); val res$2 = 
  1.point[Option];System.out.println("""res2: Option[Int] = """ + $show(res$2));$skip(30); val res$3 = 
  1.point[Option] map {_ + 2};System.out.println("""res3: Option[Int] = """ + $show(res$3));$skip(28); val res$4 = 
  1.point[List] map {_ + 2};System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(160); val res$5 = 
  
  //apply <*> - Gets the function inside a Functor and maps it over another Functor
  //in this sample, Option is a Functor
  9.some <*> {(_: Int) + 3}.some;System.out.println("""res5: Option[Int] = """ + $show(res$5));$skip(37); val res$6 = 
  ((_: Int) + (_: Int)).curried.some;System.out.println("""res6: Option[Int => (Int => Int)] = """ + $show(res$6));$skip(61); val res$7 = 
  3.some <*> {9.some <*> {(_: Int) + (_: Int)}.curried.some};System.out.println("""res7: Option[Int] = """ + $show(res$7));$skip(193); val res$8 = 
  
  
  // ^ is a shortcut for apply2, ^^ for apply3, etc. allowing muiltiple containers to be applied.
  // though |@| seems to be preferred.
  ^(List(1, 2, 3), List(10, 100, 1000)) { _ * _ };System.out.println("""res8: List[Int] = """ + $show(res$8));$skip(61); val res$9 = 
  
  
  //return the lhs (<*) or rhs (*>)
  1.some <* 2.some;System.out.println("""res9: Option[Int] = """ + $show(res$9));$skip(17); val res$10 = 
  none <* 2.some;System.out.println("""res10: Option[Nothing] = """ + $show(res$10));$skip(19); val res$11 = 
  1.some *> 2.some;System.out.println("""res11: Option[Int] = """ + $show(res$11));$skip(17); val res$12 = 
  none *> 2.some;System.out.println("""res12: Option[Int] = """ + $show(res$12));$skip(129); val res$13 = 
  
  
  //Applicative Builder |@| - Extracts values from containers and applies them to a function
  (3.some |@| 5.some) {_ + _};System.out.println("""res13: Option[Int] = """ + $show(res$13));$skip(112); val res$14 = 
  
  //lists are applicative functors
  List(1, 2, 3) <*> List((_: Int) * 0, (_: Int) + 100, (x: Int) => x * x);System.out.println("""res14: List[Int] = """ + $show(res$14));$skip(64); val res$15 = 
  
  (List("ha", "heh", "hmm") |@| List("?", "!", ".")) {_ + _};System.out.println("""res15: List[String] = """ + $show(res$15));$skip(80); 
  
  // with functions
  val f = ({(_: Int) * 2} |@| {(_: Int) + 10}) { _ + _ };System.out.println("""f  : Int => Int = """ + $show(f ));$skip(7); val res$16 = 
  f(5);System.out.println("""res16: Int = """ + $show(res$16));$skip(75); val res$17 = 
  
  
  
  // Monoidal applicatives
  Monoid[Int].applicative.ap2(1, 1)(0);System.out.println("""res17: Int = """ + $show(res$17));$skip(59); val res$18 = 
  Monoid[List[Int]].applicative.ap2(List(1), List(1))(Nil);System.out.println("""res18: List[Int] = """ + $show(res$18));$skip(107); val res$19 = 
  
  // Combining applicative functors
  // Product of List and Option
  Applicative[List].product[Option];System.out.println("""res19: scalaz.Applicative[[?](List[?], Option[?])] = """ + $show(res$19));$skip(45); val res$20 = 
  Applicative[List].product[Option].point(1);System.out.println("""res20: (List[Int], Option[Int]) = """ + $show(res$20));$skip(127); val res$21 = 
  
  // Product seems to be implemented as a Tuple2, lets append them:
  ((List(1), 1.some) |@| (List(1), 1.some)) { _ |+| _ };System.out.println("""res21: (List[Int], Option[Int]) = """ + $show(res$21));$skip(74); val res$22 = 
  
  
  // Let compose List and Otion
  Applicative[List].compose[Option];System.out.println("""res22: scalaz.Applicative[[?]List[Option[?]]] = """ + $show(res$22));$skip(45); val res$23 = 
  Applicative[List].compose[Option].point(1);System.out.println("""res23: List[Option[Int]] = """ + $show(res$23))}
  
  // We can compose applicatives and they remain applicatives....
}
