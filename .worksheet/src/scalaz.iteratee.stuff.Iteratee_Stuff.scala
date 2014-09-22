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
  drop[Int, Id](1) flatMap { Unit => head[Int, Id] } &= enumerate(Stream(1, 2, 3)) run
  
  
  // File input with Iteratees.
  import java.io._
  import effect._;System.out.println("""res7: scalaz.Scalaz.Id[Option[Int]] = """ + $show(res$7));$skip(155); 
  
  val er = enumReader[IO](new BufferedReader(new FileReader("eclipse.ini")));System.out.println("""er  : scalaz.iteratee.EnumeratorT[scalaz.effect.IoExceptionOr[Char],scalaz.effect.IO] = """ + $show(er ));$skip(91); val res$8 = 
  
  (head[IoExceptionOr[Char], IO] &= er).map(_ flatMap {_.toOption}).run.unsafePerformIO;System.out.println("""res8: Option[Char] = """ + $show(res$8));$skip(389); 
  
  
  // We can get the number of lines in two files combined, by composing two
  // enumerations and using our "counter" iteratee from above...
  
  def lengthOfTwoFiles(f1: File, f2: File) = {
    val l1 = length[IoExceptionOr[Char], IO] &= enumReader[IO](new BufferedReader(new FileReader(f1)))
    val l2 = l1 &= enumReader[IO](new BufferedReader(new FileReader(f2)))
    l2.run
  };System.out.println("""lengthOfTwoFiles: (f1: java.io.File, f2: java.io.File)scalaz.effect.IO[Int]""");$skip(85); val res$9 = 
  lengthOfTwoFiles(new File("eclipse.ini"), new File("notice.html")).unsafePerformIO;System.out.println("""res9: Int = """ + $show(res$9));$skip(120); 
  
  // more examples...
  val readLn = takeWhile[Char, List](_ != '\n') flatMap (ln => drop[Char, Id](1).map(_ => ln));System.out.println("""readLn  : scalaz.iteratee.IterateeT[Char,scalaz.Id.Id,List[Char]] = """ + $show(readLn ));$skip(68); val res$10 = 
  (readLn &= enumStream("Iteratees\nare\ncomposable".toStream)).run;System.out.println("""res10: scalaz.Id.Id[List[Char]] = """ + $show(res$10));$skip(107); val res$11 = 
  (collect[List[Char], List] %= readLn.sequenceI &= enumStream("Iteratees\nare\ncomposable".toStream)).run;System.out.println("""res11: scalaz.Id.Id[List[List[Char]]] = """ + $show(res$11))}
}
