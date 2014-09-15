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
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(414); 
  
  val sets = Set(1) :: Set("foo") :: HNil;System.out.println("""sets  : shapeless.::[scala.collection.immutable.Set[Int],shapeless.::[scala.collection.immutable.Set[String],shapeless.HNil]] = """ + $show(sets ));$skip(89); 
  
  // map selects cases of choose for each HList element
  val opts = sets map choose;System.out.println("""opts  : shapeless.::[Option[Int],shapeless.::[Option[String],shapeless.HNil]] = """ + $show(opts ));$skip(69); 
  
  val l = (23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil;System.out.println("""l  : shapeless.::[shapeless.::[Int,shapeless.::[String,shapeless.HNil]],shapeless.::[shapeless.HNil.type,shapeless.::[shapeless.::[Boolean,shapeless.HNil],shapeless.HNil]]] = """ + $show(l ));$skip(24); val res$0 = 
  
  l flatMap identity
  
  
  
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
  };System.out.println("""res0: shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]] = """ + $show(res$0));$skip(727); 
  
  val hl = 23 :: "foo" :: (13, "wibble") :: HNil;System.out.println("""hl  : shapeless.::[Int,shapeless.::[String,shapeless.::[(Int, String),shapeless.HNil]]] = """ + $show(hl ));$skip(26); val res$1 = 
  hl.foldLeft(0)(addSize)
  
  
  // Support for zippers
  import syntax.zipper._;System.out.println("""res1: Int = """ + $show(res$1));$skip(94); 
  val hl2 = 1 :: "foo" :: 3.0 :: HNil;System.out.println("""hl2  : shapeless.::[Int,shapeless.::[String,shapeless.::[Double,shapeless.HNil]]] = """ + $show(hl2 ));$skip(47); val res$2 = 
  hl2.toZipper.right.put(("wibble", 45)).reify
  
  
  // Shapeless allows standard Scala tuples to be manipulated in exactly the same ways as HList
  import syntax.std.tuple._;System.out.println("""res2: shapeless.::[Int,shapeless.::[(String, Int),shapeless.::[Double,shapeless.HNil]]] = """ + $show(res$2));$skip(155); val res$3 = 
  (23, "foo", true).head;System.out.println("""res3: Int = """ + $show(res$3));$skip(25); val res$4 = 
  (23, "foo", true).tail;System.out.println("""res4: (String, Boolean) = """ + $show(res$4));$skip(28); val res$5 = 
  (23, "foo", true).drop(2);System.out.println("""res5: (Boolean,) = """ + $show(res$5));$skip(28); val res$6 = 
  (23, "foo", true).take(2);System.out.println("""res6: (Int, String) = """ + $show(res$6));$skip(29); val res$7 = 
  (23, "foo", true).split(1);System.out.println("""res7: ((Int,), (String, Boolean)) = """ + $show(res$7));$skip(59); val res$8 = 
  
  // prepend, append, concatenate
  23 +: ("foo", true);System.out.println("""res8: (Int, String, Boolean) = """ + $show(res$8));$skip(22); val res$9 = 
  (23, "foo") :+ true;System.out.println("""res9: (Int, String, Boolean) = """ + $show(res$9));$skip(29); val res$10 = 
  (23, "foo") ++ (true, 2.0)
  
  
  // map, flatMap
  object option extends (Id ~> Option) {
    def apply[T](t: T) = Option(t)
  };System.out.println("""res10: (Int, String, Boolean, Double) = """ + $show(res$10));$skip(138); val res$11 = 
  
  (23, "foo", true) map option;System.out.println("""res11: (Option[Int], Option[String], Option[Boolean]) = """ + $show(res$11));$skip(50); val res$12 = 
  ((23, "foo"), (), (true, 2.0)) flatMap identity;System.out.println("""res12: (Int, String, Boolean, Double) = """ + $show(res$12));$skip(103); val res$13 = 
  
  // fold (using previous definition of addSize)
  (23, "foo", (13, "wibble")).foldLeft(0)(addSize);System.out.println("""res13: Int = """ + $show(res$13));$skip(94); val res$14 = 
  
  // conversion to `HList`s and ordinary Scala `List`s
  (23, "foo", true).productElements;System.out.println("""res14: shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]] = """ + $show(res$14));$skip(27); val res$15 = 
  (23, "foo", true).toList;System.out.println("""res15: List[Any] = """ + $show(res$15));$skip(84); val res$16 = 
  
  // zipper
  (23, ("foo", true), 2.0).toZipper.right.down.put("bar").root.reify;System.out.println("""res16: (Int, (String, Boolean), Double) = """ + $show(res$16))}
}
