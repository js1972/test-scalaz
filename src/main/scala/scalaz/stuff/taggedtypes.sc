package scalaz.stuff

import scalaz._
import Scalaz._

import Tags._

  
object taggedtypes {
  sealed trait KiloGram
  type KG = Double @@ KiloGram
  def KiloGram(a: Double): KG = Tag[Double, KiloGram](a)
                                                  //> KiloGram: (a: Double)scalaz.stuff.taggedtypes.KG
  
  val mass = KiloGram(20.0)                       //> mass  : scalaz.stuff.taggedtypes.KG = 20.0
  
  Tag.unwrap(mass) * 2                            //> res0: Double = 40.0
  2 * Tag.unwrap(mass)                            //> res1: Double = 40.0
  
  
  Multiplication(2)                               //> res2: scalaz.@@[Int,scalaz.Tags.Multiplication] = 2
  Multiplication(2) |+| Multiplication(10)        //> res3: scalaz.@@[Int,scalaz.Tags.Multiplication] = 20
  
  Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero
                                                  //> res4: scalaz.@@[Int,scalaz.Tags.Multiplication] = 10
  Tags.Disjunction(true) |+| Tags.Disjunction(false)
                                                  //> res5: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = true
                                                  
}