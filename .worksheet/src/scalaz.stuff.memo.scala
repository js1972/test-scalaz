package scalaz.stuff

import scalaz._
import Scalaz._

object memo {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(414); 

  // can't use val below as there is a bug in scala worksheet with recursiveness on partial functions.
  
  // WANRING - BUG IN SCALA WORKSHEET - THE MEMOIZATION TAKES 10 TIMES LONGER !!!!
  // DO THIS INA SCALA REPL INSTEAD .
  
  
  def slowFib: Int => Int = {
    case 0 => 0
    case 1 => 1
    case n => slowFib(n - 2) + slowFib(n - 1)
  };System.out.println("""slowFib: => Int => Int""");$skip(17); val res$0 = 
  
  slowFib(30);System.out.println("""res0: Int = """ + $show(res$0));$skip(14); val res$1 = 
  slowFib(40);System.out.println("""res1: Int = """ + $show(res$1));$skip(220); 
  //slowFib(45)
  
  
  // Now the memoized version - much faster...
  
  def memoizedFib: Int => Int = Memo.mutableHashMapMemo {
    case 0 => 0
    case 1 => 1
    case n => memoizedFib(n - 2) + memoizedFib(n - 1)
  };System.out.println("""memoizedFib: => Int => Int""");$skip(21); val res$2 = 
  
  memoizedFib(30);System.out.println("""res2: Int = """ + $show(res$2));$skip(18); val res$3 = 
  memoizedFib(40);System.out.println("""res3: Int = """ + $show(res$3))}
  //memoizedFib(45)
  
}
