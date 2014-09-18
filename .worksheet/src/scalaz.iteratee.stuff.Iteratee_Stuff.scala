package scalaz.iteratee.stuff

import scalaz._, Scalaz._
import iteratee._, Iteratee._

/**
 * Enumerators are data producers and Iteratee's are data consumers...
 * The &= function allows us to combine the producers with the consumer.
 *
 */
object Iteratee_Stuff {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(519); 

  // create our own iteratee
  
  def myCounter[E]: Iteratee[E, Int] = {
    def step(acc: Int)(s: Input[E]): Iteratee[E, Int] =
      s(el = e => cont(step(acc + 1)), empty = cont(step(acc)), eof = done(acc, eofInput[E]))
      
    cont(step(0))
  };System.out.println("""myCounter: [E]=> scalaz.iteratee.Iteratee[E,Int]""");$skip(68); val res$0 = 
  
  // run it
  (myCounter[Int] &= enumerate(Stream(1, 2, 3))).run;System.out.println("""res0: scalaz.Id.Id[Int] = """ + $show(res$0));$skip(227); val res$1 = 
  
  
  
  // scalaz already provides all these folding type functions to create iteratee's.
  // See IterateeT.scala in scalaz.
  
  // length just uses fold internally...
  (length[Int, Id] &= enumerate(Stream(1, 2, 3))).run;System.out.println("""res1: scalaz.Scalaz.Id[Int] = """ + $show(res$1));$skip(51); val res$2 = 
  (sum[Int, Id] &= enumerate(Stream(1, 2, 3))).run;System.out.println("""res2: scalaz.Scalaz.Id[Int] = """ + $show(res$2));$skip(52); val res$3 = 
  (head[Int, Id] &= enumerate(Stream(1, 2, 3))).run;System.out.println("""res3: scalaz.Scalaz.Id[Option[Int]] = """ + $show(res$3));$skip(178); val res$4 = 
  
  // sum just does a monoid append...
  (sum[Int @@ Tags.Multiplication, Id] &= enumerate(Stream(Tags.Multiplication(1), Tags.Multiplication(2), Tags.Multiplication(3)))).run;System.out.println("""res4: scalaz.Scalaz.Id[scalaz.@@[Int,scalaz.Tags.Multiplication]] = """ + $show(res$4));$skip(143); 


  // drop returns Unit... but it can be composed with other Iteratee's monadically...
  (drop[Int, Id](1) &= enumerate(Stream(1, 2, 3))).run;$skip(112); 
  
  def drop1keep1[E]: Iteratee[E, Option[E]] = for {
    _ <- drop[E, Id](1)
    x <- head[E, Id]
  } yield x;System.out.println("""drop1keep1: [E]=> scalaz.iteratee.Iteratee[E,Option[E]]""");$skip(57); val res$5 = 
  
  (drop1keep1[Int] &= enumerate(Stream(1, 2, 3))).run;System.out.println("""res5: scalaz.Id.Id[Option[Int]] = """ + $show(res$5));$skip(93); val res$6 = 
  // looks nicer without the brackets...
  drop1keep1[Int] &= enumerate(Stream(1, 2, 3)) run;System.out.println("""res6: scalaz.Id.Id[Option[Int]] = """ + $show(res$6));$skip(163); val res$7 = 
  
  // here's the flatmap way... I think the for comprehension is clearer!
  drop[Int, Id](1) flatMap { Unit => head[Int, Id] } &= enumerate(Stream(1, 2, 3)) run;System.out.println("""res7: scalaz.Scalaz.Id[Option[Int]] = """ + $show(res$7))}
}
