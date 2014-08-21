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
  }
  
  KnightPos(6, 2).move                            //> res0: List[scalaz.stuff.KnightsQuestMonads.KnightPos] = List(KnightPos(8,1)
                                                  //| , KnightPos(8,3), KnightPos(4,1), KnightPos(4,3), KnightPos(7,4), KnightPos
                                                  //| (5,4))
  KnightPos(6, 2).in3                             //> res1: List[scalaz.stuff.KnightsQuestMonads.KnightPos] = List(KnightPos(8,1)
                                                  //| , KnightPos(8,3), KnightPos(4,1), KnightPos(4,3), KnightPos(7,4), KnightPos
                                                  //| (5,4), KnightPos(5,2), KnightPos(5,4), KnightPos(8,1), KnightPos(8,5), Knig
                                                  //| htPos(6,1), KnightPos(6,5), KnightPos(8,1), KnightPos(8,3), KnightPos(4,1),
                                                  //|  KnightPos(4,3), KnightPos(7,4), KnightPos(5,4), KnightPos(8,3), KnightPos(
                                                  //| 8,5), KnightPos(4,3), KnightPos(4,5), KnightPos(7,2), KnightPos(7,6), Knigh
                                                  //| tPos(5,2), KnightPos(5,6), KnightPos(5,2), KnightPos(8,3), KnightPos(6,3), 
                                                  //| KnightPos(5,4), KnightPos(5,6), KnightPos(8,3), KnightPos(8,7), KnightPos(6
                                                  //| ,3), KnightPos(6,7), KnightPos(8,1), KnightPos(8,3), KnightPos(4,1), Knight
                                                  //| Pos(4,3), KnightPos(7,4), KnightPos(5,4), KnightPos(4,1), KnightPos(4,3), K
                                                  //| nightPos(3,4), KnightPos(1,4), KnightPos(7,2), KnightPos(7,4), KnightPos(3,
                                                  //| 2), KnightPos(3,4), KnightPos(6,1), KnightPos(6,5), KnightPos(4,1), KnightP
                                                  //| os(4,5), KnightPos(5,2)
                                                  //| Output exceeds cutoff limit.
  
  KnightPos(6, 2) canReachIn3 KnightPos(6, 1)     //> res2: Boolean = true
  KnightPos(6, 2) canReachIn3 KnightPos(7, 3)     //> res3: Boolean = false
  
}