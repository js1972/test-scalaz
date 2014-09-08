package scalaz.foldable.stuff

import scalaz._
import Scalaz._

/**
 * Also see folding.sc
 */
object FoldTest {

  def main(args: Array[String]): Unit = {
    val l = List(1, 2, 3, 4)
    
    println("Standard fold operation: " + l.fold(0)((x, y) => x + y))
    
    println
    println("Foldable's foldMap: " + l.foldMap { identity })
    
    println
    println("Manual coercion to a Foldable\n(scalac can't seem to find the Foldable typeclass instance): " + ToFoldableOps(l).fold)
    
    println
    println("Manual coercion (another way): " + implicitly[Foldable[List]].fold(l))
    
    println
    println("Using types to ensure a Foldable\n(wrapped in a function for typing (allows scalac to find the Foldable typeclass instance): " + sumGeneric(l))
    
    println
    println("Now using tagged types with coercion to a Foldable\n(scalac can't seem to find the Foldable typeclass instance): " + ToFoldableOps(Tags.Disjunction.subst(List(true, false, true, true))).fold)
    
    println
    val b = Tags.Disjunction.subst(List(true, false, true, true))
    println("Using tagged types - wrapped in a function for typing\n(allows scalac to find the Foldable typeclass instance): " + sumGeneric(b))
  }
  
  def sumGeneric[F[_], A](fa: F[A])(implicit F: Foldable[F], A: Monoid[A]): A =
    fa.fold

}