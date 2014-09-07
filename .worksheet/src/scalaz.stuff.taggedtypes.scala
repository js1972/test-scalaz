package scalaz.stuff

import scalaz._
import Scalaz._

import Tags._


/**
 * Also see folding.sc for more stuff on tagged types.
 */
object taggedtypes {
  sealed trait KiloGram
  type KG = Double @@ KiloGram;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(266); 
  def KiloGram(a: Double): KG = Tag[Double, KiloGram](a);System.out.println("""KiloGram: (a: Double)scalaz.stuff.taggedtypes.KG""");$skip(31); 
  
  val mass = KiloGram(20.0);System.out.println("""mass  : scalaz.stuff.taggedtypes.KG = """ + $show(mass ));$skip(26); val res$0 = 
  
  Tag.unwrap(mass) * 2;System.out.println("""res0: Double = """ + $show(res$0));$skip(23); val res$1 = 
  2 * Tag.unwrap(mass)
  
  
  sealed trait JoulePerKiloGram;System.out.println("""res1: Double = """ + $show(res$1));$skip(123); 
  def JoulePerKiloGram[A](a: A): A @@ JoulePerKiloGram = Tag[A, JoulePerKiloGram](a);System.out.println("""JoulePerKiloGram: [A](a: A)scalaz.@@[A,scalaz.stuff.taggedtypes.JoulePerKiloGram]""");$skip(134); 
  
  def energyR(m: Double @@ KiloGram): Double @@ JoulePerKiloGram =
    JoulePerKiloGram(299792458.0 * 299792458.0 * Tag.unwrap(m));System.out.println("""energyR: (m: scalaz.@@[Double,scalaz.stuff.taggedtypes.KiloGram])scalaz.@@[Double,scalaz.stuff.taggedtypes.JoulePerKiloGram]""");$skip(19); val res$2 = 
  
  energyR(mass);System.out.println("""res2: scalaz.@@[Double,scalaz.stuff.taggedtypes.JoulePerKiloGram] = """ + $show(res$2));$skip(232); val res$3 = 
  //energyR(10.0) -> produces errror "type mismatch; found : Double(10.0) required:
  //                                  scalaz.@@[Double,scalaz.stuff.taggedtypes.KiloGram]"
  
  
  
  Tags.First('a'.some) |+| Tags.First('b'.some);System.out.println("""res3: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$3));$skip(58); val res$4 = 
  Tags.First(none: Option[Char]) |+| Tags.First('b'.some);System.out.println("""res4: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$4));$skip(58); val res$5 = 
  Tags.First('a'.some) |+| Tags.First(none: Option[Char]);System.out.println("""res5: scalaz.@@[Option[Char],scalaz.Tags.First] = """ + $show(res$5));$skip(46); val res$6 = 
  Tags.Last('a'.some) |+| Tags.Last('b'.some);System.out.println("""res6: scalaz.@@[Option[Char],scalaz.Tags.Last] = """ + $show(res$6));$skip(26); val res$7 = 
  
  
  Multiplication(2);System.out.println("""res7: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$7));$skip(43); val res$8 = 
  Multiplication(2) |+| Multiplication(10);System.out.println("""res8: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$8));$skip(73); val res$9 = 
  
  Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero;System.out.println("""res9: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$9));$skip(53); val res$10 = 
  Tags.Disjunction(true) |+| Tags.Disjunction(false);System.out.println("""res10: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$10));$skip(68); val res$11 = 
  
  Tags.Multiplication(BigDecimal(-1)) |+| Tags.Multiplication(5);System.out.println("""res11: scalaz.@@[scala.math.BigDecimal,scalaz.Tags.Multiplication] = """ + $show(res$11))}
}
