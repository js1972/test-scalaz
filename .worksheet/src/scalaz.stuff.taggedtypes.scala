package scalaz.stuff

import scalaz._
import Scalaz._

import Tags._


/**
 * Also see folding.sc for more stuff on tagged types.
 */
object taggedtypes {
  sealed trait KiloGram
  type KG = Double @@ KiloGram;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(270); 
  def KiloGram(a: Double): KG = Tag[Double, KiloGram](a);System.out.println("""KiloGram: (a: Double)scalaz.stuff.taggedtypes.KG""");$skip(32); 
  
  val mass = KiloGram(20.0);System.out.println("""mass  : scalaz.stuff.taggedtypes.KG = """ + $show(mass ));$skip(27); val res$0 = 
  
  Tag.unwrap(mass) * 2;System.out.println("""res0: Double = """ + $show(res$0));$skip(23); val res$1 = 
  2 * Tag.unwrap(mass)
  
  
  sealed trait JoulePerKiloGram;System.out.println("""res1: Double = """ + $show(res$1));$skip(125); 
  def JoulePerKiloGram[A](a: A): A @@ JoulePerKiloGram = Tag[A, JoulePerKiloGram](a);System.out.println("""JoulePerKiloGram: [A](a: A)scalaz.@@[A,scalaz.stuff.taggedtypes.JoulePerKiloGram]""");$skip(135); 
  
  def energyR(m: Double @@ KiloGram): Double @@ JoulePerKiloGram =
    JoulePerKiloGram(299792458.0 * 299792458.0 * Tag.unwrap(m));System.out.println("""energyR: (m: scalaz.@@[Double,scalaz.stuff.taggedtypes.KiloGram])scalaz.@@[Double,scalaz.stuff.taggedtypes.JoulePerKiloGram]""");$skip(20); val res$2 = 
  
  energyR(mass);System.out.println("""res2: scalaz.@@[Double,scalaz.stuff.taggedtypes.JoulePerKiloGram] = """ + $show(res$2));$skip(235); val res$3 = 
  //energyR(10.0) -> produces errror "type mismatch; found : Double(10.0) required:
  //                                  scalaz.@@[Double,scalaz.stuff.taggedtypes.KiloGram]"
  
  
  
  Tags.First('a'.some) |+| Tags.First('b'.some);System.out.println("""res3: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$3));$skip(58); val res$4 = 
  Tags.First(none: Option[Char]) |+| Tags.First('b'.some);System.out.println("""res4: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$4));$skip(58); val res$5 = 
  Tags.First('a'.some) |+| Tags.First(none: Option[Char]);System.out.println("""res5: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$5));$skip(46); val res$6 = 
  Tags.Last('a'.some) |+| Tags.Last('b'.some);System.out.println("""res6: scalaz.@@[Option[Char],scalaz.Tags.Last] = """ + $show(res$6));$skip(28); val res$7 = 
  
  
  Multiplication(2);System.out.println("""res7: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$7));$skip(43); val res$8 = 
  Multiplication(2) |+| Multiplication(10);System.out.println("""res8: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$8));$skip(74); val res$9 = 
  
  Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero;System.out.println("""res9: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$9));$skip(53); val res$10 = 
  Tags.Disjunction(true) |+| Tags.Disjunction(false);System.out.println("""res10: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$10));$skip(69); val res$11 = 
  
  Tags.Multiplication(BigDecimal(-1)) |+| Tags.Multiplication(5)
  
  
  // Tags are not only useful for selecting typeclass instances
  // lets create our own tag, named Sorted which indicates a List that has been sorted
  sealed trait Sorted;System.out.println("""res11: scalaz.@@[scala.math.BigDecimal,scalaz.Tags.Multiplication] = """ + $show(res$11));$skip(209); 
  val Sorted = Tag.of[Sorted];System.out.println("""Sorted  : scalaz.Tag.TagOf[scalaz.stuff.taggedtypes.Sorted] = """ + $show(Sorted ));$skip(150); 

  // a sort function which will sort then add the Tag
  def sortList[A: scala.math.Ordering](as: List[A]): List[A] @@ Sorted =
    Sorted(as.sorted);System.out.println("""sortList: [A](as: List[A])(implicit evidence$1: scala.math.Ordering[A])scalaz.@@[List[A],scalaz.stuff.taggedtypes.Sorted]""");$skip(168); 

  // now we can define a function which takes lists which are tagged as being sorted
  def minOption[A](a: List[A] @@ Sorted): Option[A] = Sorted.unwrap(a).headOption;System.out.println("""minOption: [A](a: scalaz.@@[List[A],scalaz.stuff.taggedtypes.Sorted])Option[A]""");$skip(140); 

  // why is this implicit conversion needed - works fine without it???
  implicit val ord = implicitly[Order[Option[Int]]].toScalaOrdering;System.out.println("""ord  : scala.math.Ordering[Option[Int]] = """ + $show(ord ));$skip(39); val res$12 = 
  minOption(sortList(List(3,2,1,5,3)));System.out.println("""res12: Option[Int] = """ + $show(res$12));$skip(59); 
  assert(minOption(sortList(List(3,2,1,5,3))) === Some(1))}
}
