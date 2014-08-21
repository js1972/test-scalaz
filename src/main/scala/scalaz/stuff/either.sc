package scalaz.stuff

import scalaz._
import Scalaz._

object either {

  // right and left methods are injected into all data types fir scalaz IdOps:
  1.right[String]                                 //> res0: scalaz.\/[String,Int] = \/-(1)
  "error".left[Int]                               //> res1: scalaz.\/[String,Int] = -\/(error)
  
  // The standard scala library Either type is not a monad so does not implement flatmap
  "boom".left[Int]                                //> res2: scalaz.\/[String,Int] = -\/(boom)
  "boom".left[Int] >>= { x => (x + 1).right }     //> res3: scalaz.\/[String,Int] = -\/(boom)
  
  for {
    e1 <- "event 1 ok".right
    e2 <- "event 2 failed!".left[String]
    e3 <- "event 3 failed!".left[String]
  } yield (e1 |+| e2 |+| e3)                      //> res4: scalaz.\/[String,String] = -\/(event 2 failed!)
  
  "event 1 ok".right.isRight                      //> res5: Boolean = true
  "event 1 ok".right.isLeft                       //> res6: Boolean = false
  
  // for right side | is an alias for getOrElse
  "event 1 ok".right | "something bad"            //> res7: String = event 1 ok
  ~"event 2 failed!".left[String] | "something good"
                                                  //> res8: String = event 2 failed!
  
  // can use map to modify right side
  "event 1 ok".right map { _ + "!" }              //> res9: scalaz.\/[Nothing,String] = \/-(event 1 ok!)
  
  // to chain on the left side there's orElse (alias |||)
  "event 1 failed!".left ||| "retry event 1 ok".right
                                                  //> res10: scalaz.\/[String,String] = \/-(retry event 1 ok)
}