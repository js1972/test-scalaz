package scalaz.stuff

import scalaz._
import Scalaz._

import Tags._

  
object taggedtypes {
  sealed trait KiloGram
  type KG = Double @@ KiloGram;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(205); 
  def KiloGram(a: Double): KG = Tag[Double, KiloGram](a);System.out.println("""KiloGram: (a: Double)scalaz.stuff.taggedtypes.KG""");$skip(31); 
  
  val mass = KiloGram(20.0);System.out.println("""mass  : scalaz.stuff.taggedtypes.KG = """ + $show(mass ));$skip(26); val res$0 = 
  
  Tag.unwrap(mass) * 2;System.out.println("""res0: Double = """ + $show(res$0));$skip(23); val res$1 = 
  2 * Tag.unwrap(mass);System.out.println("""res1: Double = """ + $show(res$1));$skip(26); val res$2 = 
  
  
  Multiplication(2);System.out.println("""res2: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$2));$skip(43); val res$3 = 
  Multiplication(2) |+| Multiplication(10);System.out.println("""res3: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$3));$skip(73); val res$4 = 
  
  Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero;System.out.println("""res4: scalaz.@@[Int,scalaz.Tags.Multiplication] = """ + $show(res$4));$skip(53); val res$5 = 
  Tags.Disjunction(true) |+| Tags.Disjunction(false);System.out.println("""res5: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$5))}
                                                  
}
