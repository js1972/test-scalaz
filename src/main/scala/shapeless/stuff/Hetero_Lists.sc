package shapeless.stuff

import shapeless._
import poly._

/**
 * From https://github.com/milessabin/shapeless/wiki/Feature-overview:-shapeless-2.0.0#polymorphic-function-values
 */
object Hetero_Lists {

  // choose is a function from Sets to Options with no type specific cases
  object choose extends (Set ~> Option) {
    def apply[T](s : Set[T]) = s.headOption
  }
  
  val sets = Set(1) :: Set("foo") :: HNil         //> sets  : shapeless.::[scala.collection.immutable.Set[Int],shapeless.::[scala.
                                                  //| collection.immutable.Set[String],shapeless.HNil]] = Set(1) :: Set(foo) :: HN
                                                  //| il
  
  // map selects cases of choose for each HList element
  val opts = sets map choose                      //> opts  : shapeless.::[Option[Int],shapeless.::[Option[String],shapeless.HNil]
                                                  //| ] = Some(1) :: Some(foo) :: HNil
  
  val l = (23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil
                                                  //> l  : shapeless.::[shapeless.::[Int,shapeless.::[String,shapeless.HNil]],shap
                                                  //| eless.::[shapeless.HNil.type,shapeless.::[shapeless.::[Boolean,shapeless.HNi
                                                  //| l],shapeless.HNil]]] = 23 :: foo :: HNil :: HNil :: true :: HNil :: HNil
  
  l flatMap identity                              //> res0: shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HN
                                                  //| il]]] = 23 :: foo :: true :: HNil
  
  
  
  // HLists have polymorphic fold operations which take a polymorphic binary
  // function (addSize) (which relies on the previous size function).
  
  // size is a function from Ints or Strings or pairs to a 'size' defined
  // by type specific cases
  object size extends Poly1 {
    implicit def caseInt = at[Int](x => 1)
    implicit def caseString = at[String](_.length)
    implicit def caseTuple[T, U](implicit st: Case.Aux[T, Int], su: Case.Aux[U, Int]) =
      at[(T, U)](t => size(t._1) + size(t._2))
  }
  
  object addSize extends Poly2 {
    implicit  def default[T](implicit st: size.Case.Aux[T, Int]) =
      at[Int, T]{ (acc, t) => acc+size(t) }
  }
  
  val hl = 23 :: "foo" :: (13, "wibble") :: HNil  //> hl  : shapeless.::[Int,shapeless.::[String,shapeless.::[(Int, String),shape
                                                  //| less.HNil]]] = 23 :: foo :: (13,wibble) :: HNil
  hl.foldLeft(0)(addSize)                         //> res1: Int = 11
  
  
  // Support for zippers
  import syntax.zipper._
  val hl2 = 1 :: "foo" :: 3.0 :: HNil             //> hl2  : shapeless.::[Int,shapeless.::[String,shapeless.::[Double,shapeless.H
                                                  //| Nil]]] = 1 :: foo :: 3.0 :: HNil
  hl2.toZipper.right.put(("wibble", 45)).reify    //> res2: shapeless.::[Int,shapeless.::[(String, Int),shapeless.::[Double,shape
                                                  //| less.HNil]]] = 1 :: (wibble,45) :: 3.0 :: HNil
  
  
  // Shapeless allows standard Scala tuples to be manipulated in exactly the same ways as HList
  import syntax.std.tuple._
  (23, "foo", true).head                          //> res3: Int = 23
  (23, "foo", true).tail                          //> res4: (String, Boolean) = (foo,true)
  (23, "foo", true).drop(2)                       //> res5: (Boolean,) = (true,)
  (23, "foo", true).take(2)                       //> res6: (Int, String) = (23,foo)
  (23, "foo", true).split(1)                      //> res7: ((Int,), (String, Boolean)) = ((23,),(foo,true))
  
  // prepend, append, concatenate
  23 +: ("foo", true)                             //> res8: (Int, String, Boolean) = (23,foo,true)
  (23, "foo") :+ true                             //> res9: (Int, String, Boolean) = (23,foo,true)
  (23, "foo") ++ (true, 2.0)                      //> res10: (Int, String, Boolean, Double) = (23,foo,true,2.0)
  
  
  // map, flatMap
  object option extends (Id ~> Option) {
    def apply[T](t: T) = Option(t)
  }
  
  (23, "foo", true) map option                    //> res11: (Option[Int], Option[String], Option[Boolean]) = (Some(23),Some(foo)
                                                  //| ,Some(true))
  ((23, "foo"), (), (true, 2.0)) flatMap identity //> res12: (Int, String, Boolean, Double) = (23,foo,true,2.0)
  
  // fold (using previous definition of addSize)
  (23, "foo", (13, "wibble")).foldLeft(0)(addSize)//> res13: Int = 11
  
  // conversion to `HList`s and ordinary Scala `List`s
  (23, "foo", true).productElements               //> res14: shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.
                                                  //| HNil]]] = 23 :: foo :: true :: HNil
  (23, "foo", true).toList                        //> res15: List[Any] = List(23, foo, true)
  
  // zipper
  (23, ("foo", true), 2.0).toZipper.right.down.put("bar").root.reify
                                                  //> res16: (Int, (String, Boolean), Double) = (23,(bar,true),2.0)
}