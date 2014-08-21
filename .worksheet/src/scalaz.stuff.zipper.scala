package scalaz.stuff

import scalaz._
import Scalaz._

object zipper {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(152); val res$0 = 

  // zipper is like TreeLoc - its a window into a stream
  
  Stream(1, 2, 3, 4);System.out.println("""res0: scala.collection.immutable.Stream[Int] = """ + $show(res$0));$skip(30); val res$1 = 
  Stream(1, 2, 3, 4).toZipper;System.out.println("""res1: Option[scalaz.Zipper[Int]] = """ + $show(res$1));$skip(48); val res$2 = 
  
  Stream(1, 2, 3, 4).toZipper >>= { _.next };System.out.println("""res2: Option[scalaz.Zipper[Int]] = """ + $show(res$2));$skip(60); val res$3 = 
  Stream(1, 2, 3, 4).toZipper >>= { _.next } >>= { _.next };System.out.println("""res3: Option[scalaz.Zipper[Int]] = """ + $show(res$3));$skip(78); val res$4 = 
  Stream(1, 2, 3, 4).toZipper >>= { _.next} >>= { _.next } >>= { _.previous };System.out.println("""res4: Option[scalaz.Zipper[Int]] = """ + $show(res$4));$skip(104); 
  
  val s = Stream(1, 2, 3, 4).toZipper >>= { _.next } >>= { _.next } >>= { _.modify { _ => 7 }.some };System.out.println("""s  : Option[scalaz.Zipper[Int]] = """ + $show(s ));$skip(24); val res$5 = 
  s.get.toStream.toList;System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(159); val res$6 = 
  
  // Can also do this using for syntax
  
  for {
    z <- Stream(1, 2, 3, 4).toZipper
    n1 <- z.next
    n2 <- n1.next
  } yield ( n2.modify { _ => 7 });System.out.println("""res6: Option[scalaz.Zipper[Int]] = """ + $show(res$6))}
}
