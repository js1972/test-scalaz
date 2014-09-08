package scalaz.stuff

import scalaz._
import Scalaz._
import Tags._


/**
 * Also see taggedtypes.sc.
 */
object folding {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(163); val res$0 = 
  List(1, 2, 3).foldRight (1) {_ * _};System.out.println("""res0: Int = """ + $show(res$0));$skip(29); val res$1 = 
  9.some.foldLeft(2) {_ + _};System.out.println("""res1: Int = """ + $show(res$1));$skip(45); val res$2 = 
  
  
  List(1, 2, 3) foldMap { identity };System.out.println("""res2: Int = """ + $show(res$2));$skip(19); val res$3 = 
  Tags.Disjunction;System.out.println("""res3: scalaz.Tag.TagOf[scalaz.Tags.Disjunction] = """ + $show(res$3));$skip(53); val res$4 = 
  Tags.Disjunction(true) |+| Tags.Disjunction(false);System.out.println("""res4: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$4));$skip(60); val res$5 = 
  
  Tags.MinVal.unwrap(Tags.MinVal(1) |+| Tags.MinVal(2));System.out.println("""res5: Int = """ + $show(res$5));$skip(50); val res$6 = 
  Tags.Disjunction.unwrap(Tags.Disjunction(true));System.out.println("""res6: Boolean = """ + $show(res$6));$skip(408); val res$7 = 
  
  
  
  // Lets use the tagged type for multiplication to fold over a list.
  // Whats happening here is that foldMap needs a function that takes an Int
  // and returns a Monoid - In this case we are returning the Int tagged as
  // the type Int @@ Multiplication. Scalaz has an implicit instance to turn
  // this into a Monoid with multiplication as the mappend operation.
  
  Tags.Multiplication;System.out.println("""res7: scalaz.Tag.TagOf[scalaz.Tags.Multiplication] = """ + $show(res$7));$skip(26); val res$8 = 
  Tags.Multiplication(10);System.out.println("""res8: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$8));$skip(54); val res$9 = 
  List(1, 2, 3) foldMap { Tags.Multiplication.apply };System.out.println("""res9: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$9));$skip(166); val res$10 = 
 
  
  
  // Lets use foldMap again but with a boolean operation (Disjunction which is OR).
  
  List(true, false, true, true) foldMap { Tags.Disjunction.apply };System.out.println("""res10: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$10));$skip(461); 
  
  
  
  // Note that the apply method is called every time in the list which is a waste.
  // Instead we can use Tags.Disjunction.subst to convert the List of Ints to a
  // List of tagged Ints instead (Int @@ Tags.Disjunction). We can then just use
  // fold over the tagged types as per normal.
  // Or further below I've shown how we can still use foldMap with the identity function.
  
  val l = Tags.Disjunction.subst(List(true, false, true, true));System.out.println("""l  : List[scalaz.@@[Boolean,scalaz.Tags.Disjunction]] = """ + $show(l ));$skip(102); val res$11 = 
  
  // this one uses the standard scala fold on Traversable
  l.fold(Disjunction(false)) { _ |+| _ };System.out.println("""res11: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$11));$skip(80); val res$12 = 
  
  // now lets try get it to use Foldable from scalaz
  ToFoldableOps(l).fold;System.out.println("""res12: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$12));$skip(37); val res$13 = 
  implicitly[Foldable[List]].fold(l);System.out.println("""res13: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$13));$skip(25); 
  
  
  val x = l.head;System.out.println("""x  : scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(x ));$skip(46); 
  val y: Boolean = Tags.Disjunction.unwrap(x);System.out.println("""y  : Boolean = """ + $show(y ));$skip(29); val res$14 = 
  
  l foldMap { identity };System.out.println("""res14: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$14));$skip(84); val res$15 = 
  
  Tags.Disjunction.subst(List(false, false, false, false)) foldMap { identity };System.out.println("""res15: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$15))}
  
  // See FoldTest.scala for more examples and how to simplify further with fold
  
}
