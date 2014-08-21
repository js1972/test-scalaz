package scalaz.stuff

import scalaz._
import Scalaz._

object either {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(168); val res$0 = 

  // right and left methods are injected into all data types fir scalaz IdOps:
  1.right[String];System.out.println("""res0: scalaz.\/[String,Int] = """ + $show(res$0));$skip(20); val res$1 = 
  "error".left[Int];System.out.println("""res1: scalaz.\/[String,Int] = """ + $show(res$1));$skip(111); val res$2 = 
  
  // The standard scala library Either type is not a monad so does not implement flatmap
  "boom".left[Int];System.out.println("""res2: scalaz.\/[String,Int] = """ + $show(res$2));$skip(46); val res$3 = 
  "boom".left[Int] >>= { x => (x + 1).right };System.out.println("""res3: scalaz.\/[String,Int] = """ + $show(res$3));$skip(151); val res$4 = 
  
  for {
    e1 <- "event 1 ok".right
    e2 <- "event 2 failed!".left[String]
    e3 <- "event 3 failed!".left[String]
  } yield (e1 |+| e2 |+| e3);System.out.println("""res4: scalaz.\/[String,String] = """ + $show(res$4));$skip(32); val res$5 = 
  
  "event 1 ok".right.isRight;System.out.println("""res5: Boolean = """ + $show(res$5));$skip(28); val res$6 = 
  "event 1 ok".right.isLeft;System.out.println("""res6: Boolean = """ + $show(res$6));$skip(90); val res$7 = 
  
  // for right side | is an alias for getOrElse
  "event 1 ok".right | "something bad";System.out.println("""res7: String = """ + $show(res$7));$skip(53); val res$8 = 
  ~"event 2 failed!".left[String] | "something good";System.out.println("""res8: String = """ + $show(res$8));$skip(78); val res$9 = 
  
  // can use map to modify right side
  "event 1 ok".right map { _ + "!" };System.out.println("""res9: scalaz.\/[Nothing,String] = """ + $show(res$9));$skip(115); val res$10 = 
  
  // to chain on the left side there's orElse (alias |||)
  "event 1 failed!".left ||| "retry event 1 ok".right;System.out.println("""res10: scalaz.\/[String,String] = """ + $show(res$10))}
}
