package scalaz.stuff

import scalaz._
import Scalaz._

object KnightsQuestMonads {

  // If we have a chess board and only one knight piece on it. We want
  // to find out if the knight can reach a certain position in three moves...
  // Using Monads (list in this case)
  
  case class KnightPos(c: Int, r: Int) {
    // calculate the next possible positions
    def move: List[KnightPos] =
      for {
        KnightPos(c2, r2) <- List(
          KnightPos(c + 2, r - 1),
          KnightPos(c + 2, r + 1),
          KnightPos(c - 2, r - 1),
          KnightPos(c - 2, r + 1),
          KnightPos(c + 1, r - 2),
          KnightPos(c + 1, r + 2),
          KnightPos(c - 1, r - 2),
          KnightPos(c - 1, r + 2)
        ) if (((1 |-> 8) contains c2) && ((1 |-> 8) contains r2))
      } yield KnightPos(c2, r2)
    
    // now we try chaining this three times
    def in3: List[KnightPos] =
      for {
        first <- move
        second <- first.move
        third <- second.move
      } yield third
    
    def canReachIn3(end: KnightPos): Boolean = in3 contains end
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(1106); val res$0 = 
  
  KnightPos(6, 2).move;System.out.println("""res0: List[scalaz.stuff.KnightsQuestMonads.KnightPos] = """ + $show(res$0));$skip(22); val res$1 = 
  KnightPos(6, 2).in3;System.out.println("""res1: List[scalaz.stuff.KnightsQuestMonads.KnightPos] = """ + $show(res$1));$skip(49); val res$2 = 
  
  KnightPos(6, 2) canReachIn3 KnightPos(6, 1);System.out.println("""res2: Boolean = """ + $show(res$2));$skip(46); val res$3 = 
  KnightPos(6, 2) canReachIn3 KnightPos(7, 3);System.out.println("""res3: Boolean = """ + $show(res$3))}
  
}
