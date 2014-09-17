package shapeless.stuff

import shapeless._
import nat._
import ops.nat._
import ops.hlist._

/**
 * The goal is to determine whether a list of numbers is the appropriate length
 * (nine) and has a valid checksum, which is calculated by taking the sum of
 * each element multiplied by its distance (plus one) from the end of the list,
 * modulo eleven.
 */
object TypeTesting extends App {
  println("\n")

  // Writing a value-level implementation is easy:

  def checksum(l: List[Int]): Int = l.reverse.zipWithIndex.map {
    case (v, i) => v * (i + 1)
  }.sum % 11

  def isValid(l: List[Int]): Boolean = l.size == 9 && checksum(l) == 0

  println( isValid(List(3, 4, 5, 8, 8, 2, 8, 6, 5)) )
  println( isValid(List(3, 1, 5, 8, 8, 2, 8, 6, 5)) )



  // Suppose we want to write a type-level version instead:

  trait HasChecksum[L <: HList, S <: Nat]

  implicit object hnilHasChecksum extends HasChecksum[HNil, _0]

  implicit def hlistHasChecksum[
    H <: Nat, T <: HList, S <: Nat,
    TS <: Nat, TL <: Nat,
    HP <: Nat, HS <: Nat
  ](
    implicit
      st: HasChecksum[T, TS],
      tl: Length.Aux[T, TL],
      hp: Prod.Aux[H, Succ[TL], HP],
      hs: Sum.Aux[HP, TS, HS],
      sm: Mod.Aux[HS, _11, S]
  ) = new HasChecksum[H :: T, S] {}

  def isValid[L <: HList](implicit l: Length.Aux[L, _9], c: HasChecksum[L, _0]) = ()

  // DO NOT UN-COMMENT - CRASHES ECLIPSE !!
  //println( isValid[_3 :: _4 :: _5 :: _8 :: _8 :: _2 :: _8 :: _6 :: _5 :: HNil] )
  //isValid[_3 :: _1 :: _5 :: _8 :: _8 :: _2 :: _8 :: _6 :: _5 :: HNil]

  println("\n")
}
