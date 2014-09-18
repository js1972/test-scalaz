package scalaz.effect.stuff

import scalaz._
import Scalaz._
import effect._

object effect_st {

  import ST.{newVar, runST, newArr, returnST};import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(229); 
  
  def e1[S] = for {
    x <- newVar[S](0)
    r <- x mod { _ + 1 }
  } yield x;System.out.println("""e1: [S]=> scalaz.effect.ST[S,scalaz.effect.STRef[S,Int]]""");$skip(79); 
  
  def e2[S]: ST[S, Int] = for {
    x <- e1[S]
    r <- x.read
  } yield r
  
  
  type ForallST[A] = Forall[({type l[S] = ST[S, A]})#l];System.out.println("""e2: [S]=> scalaz.effect.ST[S,Int]""");$skip(119); val res$0 = 
 
  runST(new ForallST[Int] { def apply[S] = e2[S] });System.out.println("""res0: Int = """ + $show(res$0))}
}
