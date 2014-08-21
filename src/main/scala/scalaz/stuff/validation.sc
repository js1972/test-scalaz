package scalaz.stuff

import scalaz._
import Scalaz._

object validation {
  // these methods are added to all data types by scalaz
  "event 1 ok".success[String]                    //> res0: scalaz.Validation[String,String] = Success(event 1 ok)
  "event 1 failed!".failure[String]               //> res1: scalaz.Validation[String,String] = Failure(event 1 failed!)
  
  // Validation IS NOT A MONAD - Just an APPLICATIVE FUNCTOR
  ("event 1 ok".success[String] |@| "event 2 failed!".failure[String] |@| "event 3 failed!".failure[String]) {_ + _ + _}
                                                  //> res2: scalaz.Validation[String,String] = Failure(event 2 failed!event 3 fail
                                                  //| ed!)
  
  // Validation keeps going and reports all the failures,
  // whereas the \/ monad cuts the calulation short at the
  // first failure.
  
  // However, above, the error messages are joined together into a mess,
  // would be better as a List, which is where NEL (Non empty list) comes in.
  // A non-empty list is guaranteed to have t least one value, so that head always works.
  // Scalaz's IdOps add wrapNel tpo all data types
  1.wrapNel                                       //> res3: scalaz.NonEmptyList[Int] = NonEmptyList(1)
  
  "event 1 ok".successNel[String]                 //> res4: scalaz.ValidationNel[String,String] = Success(event 1 ok)
  "event 1 failed!".failureNel[String]            //> res5: scalaz.ValidationNel[String,String] = Failure(NonEmptyList(event 1 fai
                                                  //| led!))
  
  ("event 1 ok".successNel[String] |@| "event 2 failed!".failureNel[String] |@| "event 3 failed!".failureNel[String]) {_ + _ + _}
                                                  //> res6: scalaz.Validation[scalaz.NonEmptyList[String],String] = Failure(NonEm
                                                  //| ptyList(event 2 failed!, event 3 failed!))
}