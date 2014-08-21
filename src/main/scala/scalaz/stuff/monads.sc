package scalaz.stuff

import scalaz._
import Scalaz._


object monads {

  3.some flatMap { x => (x + 1).some }            //> res0: Option[Int] = Some(4)
  (none: Option[Int]) flatMap { x => (x + 1).some }
                                                  //> res1: Option[Int] = None
  
  Monad[Option].point("WHAT")                     //> res2: Option[String] = Some(WHAT)
  9.some flatMap { x => Monad[Option].point(x * 10) }
                                                  //> res3: Option[Int] = Some(90)
  (none: Option[Int]) flatMap { x => Monad[Option].point(x * 10) }
                                                  //> res4: Option[Int] = None
  
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
  }
  
  Pole(0, 0).landLeft(2)                          //> res5: Option[scalaz.stuff.monads.Pole] = Some(Pole(2,0))
  Pole(1, 2).landRight(1)                         //> res6: Option[scalaz.stuff.monads.Pole] = Some(Pole(1,3))
  
  // now that we wrapping Pole in a container, we can't just chain as before
  // need to flatMap...
  // also note the use of Point here to start the initial value in the Option context
  Pole(0, 0).landRight(1) flatMap { _.landLeft(2) }
                                                  //> res7: Option[scalaz.stuff.monads.Pole] = Some(Pole(2,1))
  (none: Option[Pole]) flatMap { _.landLeft(2) }  //> res8: Option[scalaz.stuff.monads.Pole] = None
  Monad[Option].point(Pole(0, 0)) flatMap { _.landRight(2) } flatMap { _.landLeft(2) } flatMap { _.landRight(2) }
                                                  //> res9: Option[scalaz.stuff.monads.Pole] = Some(Pole(2,4))
  
  // and again with the flatMap alias to make it stand out as more Monadic!
  Monad[Option].point(Pole(0, 0)) >>= { _.landRight(2)} >>= { _.landLeft(2) } >>= { _.landRight(2) }
                                                  //> res10: Option[scalaz.stuff.monads.Pole] = Some(Pole(2,4))
  
  // and now to simulate off balance... it works!
  Monad[Option].point(Pole(0, 0)) >>= { _.landLeft(1)} >>= { _.landRight(4) } >>= { _.landLeft(-1) }
                                                  //> res11: Option[scalaz.stuff.monads.Pole] = None
  
  // use the banana method to forcfe him to fall
  Monad[Option].point(Pole(0, 0)) >>= { _.landLeft(1) } >>= { _.banana } >>= { _.landRight(1) }
                                                  //> res12: Option[scalaz.stuff.monads.Pole] = None
  
  
  // some nested flatMaps (people call the anonymous functions lanmbda's sometimes)
  // note how it handles errors at any location of the nesting...
  3.some >>= { x => "!".some >>= { y => (x.shows + y).some } }
                                                  //> res13: Option[String] = Some(3!)
  3.some >>= { x => (none: Option[String]) >>= { y => (x.shows + y).some } }
                                                  //> res14: Option[String] = None
  3.some >>= { x => "!".some >>= { y => (none: Option[String]) } }
                                                  //> res15: Option[String] = None
  
  // For comprehendion notation: much simpler looking syntax!
  // remember that shows is show(s) as a String
  3.some.shows                                    //> res16: String = Some(3)
  
  for {
    x <- 3.some
    y <- "!".some
  } yield (x.shows + y)                           //> res17: Option[String] = Some(3!)
  
  
  // Expressing the pole balancing example using for notation:
  Monad[Option].point(0, 0)                       //> res18: Option[(Int, Int)] = Some((0,0))
  def routine: Option[Pole] =
    for {
      start <- Monad[Option].point(Pole(0, 0))
      first <- start.landLeft(2)
      second <- first.landRight(2)
      third <- second.landLeft(1)
    } yield third                                 //> routine: => Option[scalaz.stuff.monads.Pole]
  
  routine                                         //> res19: Option[scalaz.stuff.monads.Pole] = Some(Pole(3,2))
  
  def routine2: Option[Pole] =
    for {
      start <- Monad[Option].point(Pole(0, 0))
      first <- start.landLeft(2)
      _ <- none: Option[Pole]
      second <- first.landRight(2)
      third <- second.landLeft(1)
    } yield third                                 //> routine2: => Option[scalaz.stuff.monads.Pole]
  
  routine2                                        //> res20: Option[scalaz.stuff.monads.Pole] = None
  
  
  // some pattern matching
  def justH: Option[Char] =
    for {
      (x :: xs) <- "hello".toList.some
    } yield x                                     //> justH: => Option[Char]
    
  justH                                           //> res21: Option[Char] = Some(h)
  
  def wopwop: Option[Char] =
    for {
      (x :: xs) <- "".toList.some
    } yield x                                     //> wopwop: => Option[Char]
    
  wopwop                                          //> res22: Option[Char] = None
  
  
  List(3, 4, 5) >>= { x => List(x, -x) }          //> res23: List[Int] = List(3, -3, 4, -4, 5, -5)
  
  for {
    n <- List(1, 2)
    ch <- List('a', 'b')
  } yield (n, ch)                                 //> res24: List[(Int, Char)] = List((1,a), (1,b), (2,a), (2,b))
  
  
  // using |-> from Enum (same as 1 to 50) to create a List
  for {
    x <- 1 |-> 50 if x.shows contains '7'
  } yield x                                       //> res25: List[Int] = List(7, 17, 27, 37, 47)
  
  
  
  // Plus has the <+> operator to append two containers
  List(1, 2, 3) <+> List(4, 5, 6)                 //> res26: List[Int] = List(1, 2, 3, 4, 5, 6)
  List(1, 2, 3) <+> List(4, 5,6, 7, 8)            //> res27: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8)
  
  // MonadPlus introduces filter:
  (1 |-> 50) filter { x => x.shows contains '7' } //> res28: List[Int] = List(7, 17, 27, 37, 47)
  
  
  // |> is from IdOps.scala (along with ??) - applies self to the provided function
  3 |>  { x => (x + 100000).some }                //> res29: Option[Int] = Some(100003)
  
  
  // join method on monads - flattens out nested values
  (Some(9.some): Option[Option[Int]]).join        //> res30: Option[Int] = Some(9)
  (Some(none): Option[Option[Int]]).join          //> res31: Option[Int] = None
  (List(List(1, 2, 3), List(4, 5, 6))).join       //> res32: List[Int] = List(1, 2, 3, 4, 5, 6)
  9.right[String].right[String].join              //> res33: scalaz.\/[String,Int] = \/-(9)
  "boom".left[Int].right[String].join             //> res34: scalaz.\/[String,Int] = -\/(boom)
  
  
  // filterM - filter based on a predicate that returns a monadic value based on Boolean
  List(1, 2, 3) filterM { x => List(true, false) }//> res35: List[List[Int]] = List(List(1, 2, 3), List(1, 2), List(1, 3), List(1
                                                  //| ), List(2, 3), List(2), List(3), List())
  Vector(1, 2, 3) filterM { x => Vector(true, false) }
                                                  //> res36: scala.collection.immutable.Vector[Vector[Int]] = Vector(Vector(1, 2,
                                                  //|  3), Vector(1, 2), Vector(1, 3), Vector(1), Vector(2, 3), Vector(2), Vector
                                                  //| (3), Vector())
  
  
  // foldLeftM - monadic counterpart to foldLeft - folds with monads
  def binSmalls(acc: Int, x: Int): Option[Int] = {
    if (x > 9) (none: Option[Int])
    else (acc + x).some
  }                                               //> binSmalls: (acc: Int, x: Int)Option[Int]
  
  List(2, 8, 3, 1).foldLeftM(0) { binSmalls }     //> res37: Option[Int] = Some(14)
  List(2, 11, 3, 1).foldLeftM(0) { binSmalls }    //> res38: Option[Int] = None
}