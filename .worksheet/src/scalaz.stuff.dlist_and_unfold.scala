package scalaz.stuff

import scalaz._
import Scalaz._

object dlist_and_unfold {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(164); 
  val dl = DList.unfoldr(10, { (x: Int) => if (x == 0) none else (x, x - 1).some });System.out.println("""dl  : scalaz.DList[Int] = """ + $show(dl ));$skip(12); val res$0 = 
  dl.toList;System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(159); 
  
  
  // In Scalaz unfold defined in StreamFunctions is introduced by import Scalaz._:
  val u = unfold(10) { (x) => if (x == 0) none else (x, x - 1).some };System.out.println("""u  : Stream[Int] = """ + $show(u ));$skip(11); val res$1 = 
  u.toList;System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(277); 
  
  
  
  
  // A selection sort example - pretty funky though I don't really understand it yet!
  // Looks to be using unfold to genreate the final sorted list.
  
  def minimumS[A: Order](stream: Stream[A]) = stream match {
    case x #:: xs => xs.foldLeft(x) {_ min _}
  };System.out.println("""minimumS: [A](stream: Stream[A])(implicit evidence$2: scalaz.Order[A])A""");$skip(204); 
  
  def deleteS[A: Equal](y: A, stream: Stream[A]): Stream[A] = (y, stream) match {
    case (_, Stream()) => Stream()
    case (y, x #:: xs) =>
      if (y === x) xs
      else x #:: deleteS(y, xs)
  };System.out.println("""deleteS: [A](y: A, stream: Stream[A])(implicit evidence$3: scalaz.Equal[A])Stream[A]""");$skip(189); 
  
  def delmin[A: Order](stream: Stream[A]): Option[(A, Stream[A])] = stream match {
    case Stream() => none
    case xs =>
      val y = minimumS(xs)
      (y, deleteS(y, xs)).some
  };System.out.println("""delmin: [A](stream: Stream[A])(implicit evidence$4: scalaz.Order[A])Option[(A, Stream[A])]""");$skip(85); 
  
  def ssort[A: Order](stream: Stream[A]): Stream[A] = unfold(stream){ delmin[A] };System.out.println("""ssort: [A](stream: Stream[A])(implicit evidence$5: scalaz.Order[A])Stream[A]""");$skip(38); val res$2 = 
  
  ssort(Stream(1, 3, 4, 2)).toList;System.out.println("""res2: List[Int] = """ + $show(res$2))}
  
}
