package scalaz.stuff

object cantruthy_test {

  import ToCanIsTruthyOps._
  
  //Create a typeclass instance for Int
  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })                                              //> intCanTruthy  : scalaz.stuff.CanTruthy[Int] = scalaz.stuff.CanTruthy$$anon$1
                                                  //| @7717cddf
  
  10.truthy                                       //> res0: Boolean = true
  
  // Create a typeclass instance for Lists
  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _   => true
  })                                              //> listCanTruthy: [A]=> scalaz.stuff.CanTruthy[List[A]]
  
  List("foo").truthy                              //> res1: Boolean = true

  
  //Need to create a typeclass instance for Nil because of Nonvariance of type parameter
  implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)
                                                  //> nilCanTruthy  : scalaz.stuff.CanTruthy[collection.immutable.Nil.type] = scal
                                                  //| az.stuff.CanTruthy$$anon$1@29d971d9
  Nil.truthy                                      //> res2: Boolean = false
  
  
  //Create a typeclass instance for Boolean
  implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)
                                                  //> booleanCanTruthy  : scalaz.stuff.CanTruthy[Boolean] = scalaz.stuff.CanTruthy
                                                  //| $$anon$1@4aa192a5
  false.truthy                                    //> res3: Boolean = false


  //Lets define a truthyIf
  def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
    if (cond.truthy) ifyes
    else ifno                                     //> truthyIf: [A, B, C](cond: A)(ifyes: => B)(ifno: => C)(implicit evidence$1: s
                                                  //| calaz.stuff.CanTruthy[A])Any
  
  truthyIf(Nil) {"YEAH!"} {"NO!"}                 //> res4: Any = NO!
  truthyIf(2 :: 3 :: 4 :: Nil) {"YEAH!"} {"NO!"}  //> res5: Any = YEAH!
  truthyIf(true) {"YEAH!"} {"NO!"}                //> res6: Any = YEAH!
}