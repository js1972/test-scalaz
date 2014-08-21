package scalaz.stuff

import scalaz._
import Scalaz._

object zipper {

  // zipper is like TreeLoc - its a window into a stream
  
  Stream(1, 2, 3, 4)                              //> res0: scala.collection.immutable.Stream[Int] = Stream(1, ?)
  Stream(1, 2, 3, 4).toZipper                     //> res1: Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 1, <rights>))
  
  Stream(1, 2, 3, 4).toZipper >>= { _.next }      //> res2: Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 2, <rights>))
  Stream(1, 2, 3, 4).toZipper >>= { _.next } >>= { _.next }
                                                  //> res3: Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 3, <rights>))
  Stream(1, 2, 3, 4).toZipper >>= { _.next} >>= { _.next } >>= { _.previous }
                                                  //> res4: Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 2, <rights>))
  
  val s = Stream(1, 2, 3, 4).toZipper >>= { _.next } >>= { _.next } >>= { _.modify { _ => 7 }.some }
                                                  //> s  : Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 7, <rights>))
  s.get.toStream.toList                           //> res5: List[Int] = List(1, 2, 7, 4)
  
  // Can also do this using for syntax
  
  for {
    z <- Stream(1, 2, 3, 4).toZipper
    n1 <- z.next
    n2 <- n1.next
  } yield ( n2.modify { _ => 7 })                 //> res6: Option[scalaz.Zipper[Int]] = Some(Zipper(<lefts>, 7, <rights>))
}