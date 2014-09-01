package scalaz.stuff

import scalaz._
import Scalaz._

object dlist_and_unfold {
  val dl = DList.unfoldr(10, { (x: Int) => if (x == 0) none else (x, x - 1).some })
                                                  //> dl  : scalaz.DList[Int] = scalaz.DList@d2ac180
  dl.toList                                       //> res0: List[Int] = List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
  
  
  // In Scalaz unfold defined in StreamFunctions is introduced by import Scalaz._:
  val u = unfold(10) { (x) => if (x == 0) none else (x, x - 1).some }
                                                  //> u  : Stream[Int] = Stream(10, ?)
  u.toList                                        //> res1: List[Int] = List(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
  
  
  
  
  // A selection sort example - pretty funky though I don't really understand it yet!
  // Looks to be using unfold to genreate the final sorted list.
  
  def minimumS[A: Order](stream: Stream[A]) = stream match {
    case x #:: xs => xs.foldLeft(x) {_ min _}
  }                                               //> minimumS: [A](stream: Stream[A])(implicit evidence$2: scalaz.Order[A])A
  
  def deleteS[A: Equal](y: A, stream: Stream[A]): Stream[A] = (y, stream) match {
    case (_, Stream()) => Stream()
    case (y, x #:: xs) =>
      if (y === x) xs
      else x #:: deleteS(y, xs)
  }                                               //> deleteS: [A](y: A, stream: Stream[A])(implicit evidence$3: scalaz.Equal[A])S
                                                  //| tream[A]
  
  def delmin[A: Order](stream: Stream[A]): Option[(A, Stream[A])] = stream match {
    case Stream() => none
    case xs =>
      val y = minimumS(xs)
      (y, deleteS(y, xs)).some
  }                                               //> delmin: [A](stream: Stream[A])(implicit evidence$4: scalaz.Order[A])Option[
                                                  //| (A, Stream[A])]
  
  def ssort[A: Order](stream: Stream[A]): Stream[A] = unfold(stream){ delmin[A] }
                                                  //> ssort: [A](stream: Stream[A])(implicit evidence$5: scalaz.Order[A])Stream[A
                                                  //| ]
  
  ssort(Stream(1, 3, 4, 2)).toList                //> res2: List[Int] = List(1, 2, 3, 4)
  
}