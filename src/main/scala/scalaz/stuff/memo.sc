package scalaz.stuff

import scalaz._
import Scalaz._

object memo {

  // can't use val below as there is a bug in scala worksheet with recursiveness on partial functions.
  
  // WANRING - BUG IN SCALA WORKSHEET - THE MEMOIZATION TAKES 10 TIMES LONGER !!!!
  // DO THIS INA SCALA REPL INSTEAD .
  
  
  def slowFib: Int => Int = {
    case 0 => 0
    case 1 => 1
    case n => slowFib(n - 2) + slowFib(n - 1)
  }                                               //> slowFib: => Int => Int
  
  slowFib(30)                                     //> res0: Int = 832040
  slowFib(40)                                     //> res1: Int = 102334155
  //slowFib(45)
  
  
  // Now the memoized version - much faster...
  
  def memoizedFib: Int => Int = Memo.mutableHashMapMemo {
    case 0 => 0
    case 1 => 1
    case n => memoizedFib(n - 2) + memoizedFib(n - 1)
  }                                               //> memoizedFib: => Int => Int
  
  memoizedFib(30)                                 //> res2: Int = 832040
  memoizedFib(40)                                 //> res3: Int = 102334155
  //memoizedFib(45)
  
}