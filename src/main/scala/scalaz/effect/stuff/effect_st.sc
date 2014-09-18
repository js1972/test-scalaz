package scalaz.effect.stuff

import scalaz._
import Scalaz._
import effect._

object effect_st {

  import ST.{newVar, runST, newArr, returnST}
  
  def e1[S] = for {
    x <- newVar[S](0)
    r <- x mod { _ + 1 }
  } yield x                                       //> e1: [S]=> scalaz.effect.ST[S,scalaz.effect.STRef[S,Int]]
  
  def e2[S]: ST[S, Int] = for {
    x <- e1[S]
    r <- x.read
  } yield r                                       //> e2: [S]=> scalaz.effect.ST[S,Int]
  
  
  type ForallST[A] = Forall[({type l[S] = ST[S, A]})#l]
 
  runST(new ForallST[Int] { def apply[S] = e2[S] })
                                                  //> res0: Int = 1
}