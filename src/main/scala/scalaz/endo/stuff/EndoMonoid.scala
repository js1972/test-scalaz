package scalaz.endo.stuff

import scalaz._
import Scalaz._

/**
 * Test program showing the use of Endo and Monoid. Scalaz defines an endo
 * as a monoid, so they can be accumulated. Scalaz also add the ?? operator
 * to the Boolean type which calls the monoid append function on true and
 * the zero function on false. For an endo, the zero function is the 
 * identity endomorphism.
 * The example below is based on a situation given in the following blog
 * post: http://debasishg.blogspot.com.au/2013/03/an-exercise-in-refactoring-playing.html.
 */
object EndoMonoid {
  case class SalaryConfig(
    surcharge: Boolean    = true, 
    tax: Boolean          = true, 
    bonus: Boolean        = true,
    allowance: Boolean    = true 
  )

  // These functions transform the salary components - note that they are
  // endomorphisms: T => T.
  
  // B = basic + 20%
  val plusAllowance = (b: Double) => b * 1.2

  // C = B + 10%
  val plusBonus = (b: Double) => b * 1.1

  // D = C - 30%
  val plusTax = (b: Double) => 0.7 * b

  // E = D - 10%
  val plusSurcharge = (b: Double) => 0.9 * b

  
  // no abstraction, imperative, using var
  def computeSalaryNoAbstraction(sc: SalaryConfig, basic: Double) = {
    var salary = basic
    if (sc.allowance) salary = plusAllowance(salary)
    if (sc.bonus) salary = plusBonus(salary)
    if (sc.tax) salary = plusTax(salary)
    if (sc.surcharge) salary = plusSurcharge(salary)
    salary
  }

  // compose using mappend of endomorphism
  def computeSalary(sc: SalaryConfig, basic: Double) = {
    val e = 
      sc.surcharge ?? plusSurcharge.endo     |+|
      sc.tax ?? plusTax.endo                 |+|
      sc.bonus ?? plusBonus.endo             |+| 
      sc.allowance ?? plusAllowance.endo 
      
    e run basic
  }

  // possibly better abstracted
  def computeSalaryFold1(sc: SalaryConfig, basic: Double) = {
    val components = 
      List((sc.surcharge, plusSurcharge), 
           (sc.tax, plusTax), 
           (sc.bonus, plusBonus),
           (sc.allowance, plusAllowance)
      )
    val e = components.foldMap(e => e._1 ?? e._2.endo)
    
    e run basic
  } 

  // slightly more magical
  // foldables being zipped have to be ordered in the same sequence
  def computeSalaryFold2(sc: SalaryConfig, basic: Double) = {
    val configValues = SalaryConfig.unapply(sc).get.productIterator.map { case i: Boolean => i }.toList
    val components = List(plusSurcharge, plusTax, plusBonus, plusAllowance)
    val e = (configValues zip components).foldMap(e => e._1 ?? e._2.endo)
    e run basic
  } 

  def run = {
    println(computeSalaryNoAbstraction(SalaryConfig(), 100000))
    println(computeSalaryNoAbstraction(SalaryConfig(false, true, false, true), 100000))
    println(computeSalary(SalaryConfig(), 100000))
    println(computeSalary(SalaryConfig(false, true, false, true), 100000))
    println(computeSalaryFold1(SalaryConfig(), 100000))
    println(computeSalaryFold1(SalaryConfig(false, true, false, true), 100000))
    println(computeSalaryFold2(SalaryConfig(), 100000))
    println(computeSalaryFold2(SalaryConfig(false, true, false, true), 100000))
  }
}

object EndoMonoidTest {
  def main(args: Array[String]): Unit = EndoMonoid.run
}