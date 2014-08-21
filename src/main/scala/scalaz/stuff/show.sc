package scalaz.stuff

import scalaz._
import Scalaz._


object show {
  3.show                                          //> res0: scalaz.Cord = 3
  3.shows                                         //> res1: String = 3
  "hello".println                                 //> "hello"
  "hello"(3)                                      //> res2: Char = l
  "hello".show.split(2)                           //> res3: (scalaz.Cord, scalaz.Cord) = ("h,ello")
  "hello".size                                    //> res4: Int = 5
}