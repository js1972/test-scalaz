package scalaz.stuff

import scalaz._
import Scalaz._


object monads {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(111); val res$0 = 

  3.some flatMap { x => (x + 1).some };System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(52); val res$1 = 
  (none: Option[Int]) flatMap { x => (x + 1).some };System.out.println("""res1: Option[Int] = """ + $show(res$1));$skip(33); val res$2 = 
  
  Monad[Option].point("WHAT");System.out.println("""res2: Option[String] = """ + $show(res$2));$skip(54); val res$3 = 
  9.some flatMap { x => Monad[Option].point(x * 10) };System.out.println("""res3: Option[Int] = """ + $show(res$3));$skip(67); val res$4 = 
  (none: Option[Int]) flatMap { x => Monad[Option].point(x * 10) }
  
  // Pole balancing example:
  // Let's say that [Pierre] keeps his balance if the number of birds
  // on the left side of the pole and on the right side of the pole is
  // within three. So if there's one bird on the right side and four
  // birds on the left side, he's okay. But if the fifth bird lands on
  // the left side, then he loses his balance and takes a dive.
  
  type Birds = Int
  case class Pole(left: Birds, right: Birds) {
    def landLeft(n: Birds): Option[Pole] =
      if (math.abs((left + n) - right) < 4) copy(left = left + n).some
      else none
      
    def landRight(n: Birds): Option[Pole] =
      if (math.abs(left - (right + n)) < 4) copy(right = right + n).some
      else none
    
    def banana: Option[Pole] = none
  };System.out.println("""res4: Option[Int] = """ + $show(res$4));$skip(789); val res$5 = 
  
  Pole(0, 0).landLeft(2);System.out.println("""res5: Option[scalaz.stuff.monads.Pole] = """ + $show(res$5));$skip(26); val res$6 = 
  Pole(1, 2).landRight(1);System.out.println("""res6: Option[scalaz.stuff.monads.Pole] = """ + $show(res$6));$skip(242); val res$7 = 
  
  // now that we wrapping Pole in a container, we can't just chain as before
  // need to flatMap...
  // also note the use of Point here to start the initial value in the Option context
  Pole(0, 0).landRight(1) flatMap { _.landLeft(2) };System.out.println("""res7: Option[scalaz.stuff.monads.Pole] = """ + $show(res$7));$skip(49); val res$8 = 
  (none: Option[Pole]) flatMap { _.landLeft(2) };System.out.println("""res8: Option[scalaz.stuff.monads.Pole] = """ + $show(res$8));$skip(114); val res$9 = 
  Monad[Option].point(Pole(0, 0)) flatMap { _.landRight(2) } flatMap { _.landLeft(2) } flatMap { _.landRight(2) };System.out.println("""res9: Option[scalaz.stuff.monads.Pole] = """ + $show(res$9));$skip(180); val res$10 = 
  
  // and again with the flatMap alias to make it stand out as more Monadic!
  Monad[Option].point(Pole(0, 0)) >>= { _.landRight(2)} >>= { _.landLeft(2) } >>= { _.landRight(2) };System.out.println("""res10: Option[scalaz.stuff.monads.Pole] = """ + $show(res$10));$skip(154); val res$11 = 
  
  // and now to simulate off balance... it works!
  Monad[Option].point(Pole(0, 0)) >>= { _.landLeft(1)} >>= { _.landRight(4) } >>= { _.landLeft(-1) };System.out.println("""res11: Option[scalaz.stuff.monads.Pole] = """ + $show(res$11));$skip(148); val res$12 = 
  
  // use the banana method to forcfe him to fall
  Monad[Option].point(Pole(0, 0)) >>= { _.landLeft(1) } >>= { _.banana } >>= { _.landRight(1) };System.out.println("""res12: Option[scalaz.stuff.monads.Pole] = """ + $show(res$12));$skip(219); val res$13 = 
  
  
  // some nested flatMaps (people call the anonymous functions lanmbda's sometimes)
  // note how it handles errors at any location of the nesting...
  3.some >>= { x => "!".some >>= { y => (x.shows + y).some } };System.out.println("""res13: Option[String] = """ + $show(res$13));$skip(77); val res$14 = 
  3.some >>= { x => (none: Option[String]) >>= { y => (x.shows + y).some } };System.out.println("""res14: Option[String] = """ + $show(res$14));$skip(67); val res$15 = 
  3.some >>= { x => "!".some >>= { y => (none: Option[String]) } };System.out.println("""res15: Option[String] = """ + $show(res$15));$skip(128); val res$16 = 
  
  // For comprehendion notation: much simpler looking syntax!
  // remember that shows is show(s) as a String
  3.some.shows;System.out.println("""res16: String = """ + $show(res$16));$skip(69); val res$17 = 
  
  for {
    x <- 3.some
    y <- "!".some
  } yield (x.shows + y);System.out.println("""res17: Option[String] = """ + $show(res$17));$skip(97); val res$18 = 
  
  
  // Expressing the pole balancing example using for notation:
  Monad[Option].point(0, 0);System.out.println("""res18: Option[(Int, Int)] = """ + $show(res$18));$skip(207); 
  def routine: Option[Pole] =
    for {
      start <- Monad[Option].point(Pole(0, 0))
      first <- start.landLeft(2)
      second <- first.landRight(2)
      third <- second.landLeft(1)
    } yield third;System.out.println("""routine: => Option[scalaz.stuff.monads.Pole]""");$skip(13); val res$19 = 
  
  routine;System.out.println("""res19: Option[scalaz.stuff.monads.Pole] = """ + $show(res$19));$skip(241); 
  
  def routine2: Option[Pole] =
    for {
      start <- Monad[Option].point(Pole(0, 0))
      first <- start.landLeft(2)
      _ <- none: Option[Pole]
      second <- first.landRight(2)
      third <- second.landLeft(1)
    } yield third;System.out.println("""routine2: => Option[scalaz.stuff.monads.Pole]""");$skip(14); val res$20 = 
  
  routine2;System.out.println("""res20: Option[scalaz.stuff.monads.Pole] = """ + $show(res$20));$skip(124); 
  
  
  // some pattern matching
  def justH: Option[Char] =
    for {
      (x :: xs) <- "hello".toList.some
    } yield x;System.out.println("""justH: => Option[Char]""");$skip(13); val res$21 = 
    
  justH;System.out.println("""res21: Option[Char] = """ + $show(res$21));$skip(90); 
  
  def wopwop: Option[Char] =
    for {
      (x :: xs) <- "".toList.some
    } yield x;System.out.println("""wopwop: => Option[Char]""");$skip(14); val res$22 = 
    
  wopwop;System.out.println("""res22: Option[Char] = """ + $show(res$22));$skip(47); val res$23 = 
  
  
  List(3, 4, 5) >>= { x => List(x, -x) };System.out.println("""res23: List[Int] = """ + $show(res$23));$skip(74); val res$24 = 
  
  for {
    n <- List(1, 2)
    ch <- List('a', 'b')
  } yield (n, ch);System.out.println("""res24: List[(Int, Char)] = """ + $show(res$24));$skip(128); val res$25 = 
  
  
  // using |-> from Enum (same as 1 to 50) to create a List
  for {
    x <- 1 |-> 50 if x.shows contains '7'
  } yield x;System.out.println("""res25: List[Int] = """ + $show(res$25));$skip(99); val res$26 = 
  
  
  
  // Plus has the <+> operator to append two containers
  List(1, 2, 3) <+> List(4, 5, 6);System.out.println("""res26: List[Int] = """ + $show(res$26));$skip(39); val res$27 = 
  List(1, 2, 3) <+> List(4, 5,6, 7, 8);System.out.println("""res27: List[Int] = """ + $show(res$27));$skip(87); val res$28 = 
  
  // MonadPlus introduces filter:
  (1 |-> 50) filter { x => x.shows contains '7' };System.out.println("""res28: List[Int] = """ + $show(res$28));$skip(125); val res$29 = 
  
  
  // |> is from IdOps.scala (along with ??) - applies self to the provided function
  3 |>  { x => (x + 100000).some };System.out.println("""res29: Option[Int] = """ + $show(res$29));$skip(105); val res$30 = 
  
  
  // join method on monads - flattens out nested values
  (Some(9.some): Option[Option[Int]]).join;System.out.println("""res30: Option[Int] = """ + $show(res$30));$skip(41); val res$31 = 
  (Some(none): Option[Option[Int]]).join;System.out.println("""res31: Option[Int] = """ + $show(res$31));$skip(44); val res$32 = 
  (List(List(1, 2, 3), List(4, 5, 6))).join;System.out.println("""res32: List[Int] = """ + $show(res$32));$skip(37); val res$33 = 
  9.right[String].right[String].join;System.out.println("""res33: scalaz.\/[String,Int] = """ + $show(res$33));$skip(38); val res$34 = 
  "boom".left[Int].right[String].join;System.out.println("""res34: scalaz.\/[String,Int] = """ + $show(res$34));$skip(146); val res$35 = 
  
  
  // filterM - filter based on a predicate that returns a monadic value based on Boolean
  List(1, 2, 3) filterM { x => List(true, false) };System.out.println("""res35: List[List[Int]] = """ + $show(res$35));$skip(55); val res$36 = 
  Vector(1, 2, 3) filterM { x => Vector(true, false) };System.out.println("""res36: scala.collection.immutable.Vector[Vector[Int]] = """ + $show(res$36));$skip(189); 
  
  
  // foldLeftM - monadic counterpart to foldLeft - folds with monads
  def binSmalls(acc: Int, x: Int): Option[Int] = {
    if (x > 9) (none: Option[Int])
    else (acc + x).some
  };System.out.println("""binSmalls: (acc: Int, x: Int)Option[Int]""");$skip(49); val res$37 = 
  
  List(2, 8, 3, 1).foldLeftM(0) { binSmalls };System.out.println("""res37: Option[Int] = """ + $show(res$37));$skip(47); val res$38 = 
  List(2, 11, 3, 1).foldLeftM(0) { binSmalls };System.out.println("""res38: Option[Int] = """ + $show(res$38))}
}
