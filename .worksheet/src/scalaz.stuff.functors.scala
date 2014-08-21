package scalaz.stuff

import scalaz._
import Scalaz._


object functors {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(101); val res$0 = 
  List(1, 2, 3) map {_ + 1};System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(33); val res$1 = 
  (1, 2, 3, 4, 5, 6) map {_ + 1};System.out.println("""res1: (Int, Int, Int, Int, Int, Int) = """ + $show(res$1));$skip(33); val res$2 = 
  (1, 2, 3, 4, 5, 6) map {_ + 1};System.out.println("""res2: (Int, Int, Int, Int, Int, Int) = """ + $show(res$2));$skip(25); val res$3 = 
  
  (1, 2) map {1 + _};System.out.println("""res3: (Int, Int) = """ + $show(res$3));$skip(15); val res$4 = 
  
  (1, 2)._2;System.out.println("""res4: Int = """ + $show(res$4));$skip(17); 
  val x = (4, 3);System.out.println("""x  : (Int, Int) = """ + $show(x ));$skip(13); val res$5 = 
  x.map(1 +);System.out.println("""res5: (Int, Int) = """ + $show(res$5));$skip(27); val res$6 = 
  
  ("a" -> 2).map(_ + 1);System.out.println("""res6: (String, Int) = """ + $show(res$6));$skip(74); 
  
  
  //Functor for Funtion1
  val f = ((x: Int) => x + 1) map {_ * 7};System.out.println("""f  : Int => Int = """ + $show(f ));$skip(7); val res$7 = 
  f(3);System.out.println("""res7: Int = """ + $show(res$7));$skip(23); val res$8 = 
  
  ((_: Int) * 3)(3);System.out.println("""res8: Int = """ + $show(res$8));$skip(37); val res$9 = 
  (((_: Int) * 3) map {_ + 100}) (3);System.out.println("""res9: Int = """ + $show(res$9));$skip(31); val res$10 = 
  
  List(1, 2, 3) map {_ * 3};System.out.println("""res10: List[Int] = """ + $show(res$10));$skip(25); val res$11 = 
  List(1, 2, 3) map {3*};System.out.println("""res11: List[Int] = """ + $show(res$11));$skip(47); 
  
  val l = Functor[List].lift {(_: Int) * 2};System.out.println("""l  : List[Int] => List[Int] = """ + $show(l ));$skip(13); val res$12 = 
  l(List(3));System.out.println("""res12: List[Int] = """ + $show(res$12));$skip(26); val res$13 = 
  
  List(1, 2, 3) >| "x";System.out.println("""res13: List[String] = """ + $show(res$13));$skip(23); val res$14 = 
  List(1, 2, 3) as "x";System.out.println("""res14: List[String] = """ + $show(res$14));$skip(22); val res$15 = 
  List(1, 2, 3).fpair;System.out.println("""res15: List[(Int, Int)] = """ + $show(res$15));$skip(31); val res$16 = 
  List(1, 2, 3).strengthL("x");System.out.println("""res16: List[(String, Int)] = """ + $show(res$16));$skip(31); val res$17 = 
  List(1, 2, 3).strengthR("x");System.out.println("""res17: List[(Int, String)] = """ + $show(res$17));$skip(21); val res$18 = 
  List(1, 2, 3).void;System.out.println("""res18: List[Unit] = """ + $show(res$18));$skip(66); val res$19 = 
  
  
  
  // This is some monoid apply's ->
  1.some |+| 1.some;System.out.println("""res19: Option[Int] = """ + $show(res$19));$skip(30); val res$20 = 
  (1, 1, "a") |+| (3, 3, "a");System.out.println("""res20: (Int, Int, String) = """ + $show(res$20));$skip(78); 
  
  
  //functor law
  List(1, 2, 3) map {identity} assert_=== List(1, 2, 3);$skip(123); 
  (List(1, 2, 3) map {{(_: Int) * 3} map {(_: Int) + 1}}) assert_=== (List(1, 2, 3) map {(_: Int) * 3} map {(_: Int) + 1})}
  
  
}
