package scalaz.stuff

import scalaz._
import Scalaz._


object order {
  1 > 2.0                                         //> res0: Boolean(false) = false
  
  1.0 ?|? 2.0                                     //> res1: scalaz.Ordering = LT
  
  1.0 max 2.0                                     //> res2: Double = 2.0
}