package scalaz.iteratee.stuff

import scalaz._, Scalaz._
import iteratee._, Iteratee._

/**
 * Enumerators are data producers and Iteratee's are data consumers...
 * The &= function allows us to combine the producers with the consumer.
 *
 */
object Iteratee_Stuff {

  // create our own iteratee
  
  def myCounter[E]: Iteratee[E, Int] = {
    def step(acc: Int)(s: Input[E]): Iteratee[E, Int] =
      s(el = e => cont(step(acc + 1)), empty = cont(step(acc)), eof = done(acc, eofInput[E]))
      
    cont(step(0))
  }                                               //> myCounter: [E]=> scalaz.iteratee.Iteratee[E,Int]
  
  // run it
  (myCounter[Int] &= enumerate(Stream(1, 2, 3))).run
                                                  //> res0: scalaz.Id.Id[Int] = 3
  
  
  
  // scalaz already provides all these folding type functions to create iteratee's.
  // See IterateeT.scala in scalaz.
  
  // length just uses fold internally...
  (length[Int, Id] &= enumerate(Stream(1, 2, 3))).run
                                                  //> res1: scalaz.Scalaz.Id[Int] = 3
  (sum[Int, Id] &= enumerate(Stream(1, 2, 3))).run//> res2: scalaz.Scalaz.Id[Int] = 6
  (head[Int, Id] &= enumerate(Stream(1, 2, 3))).run
                                                  //> res3: scalaz.Scalaz.Id[Option[Int]] = Some(1)
  
  // sum just does a monoid append...
  (sum[Int @@ Tags.Multiplication, Id] &= enumerate(Stream(Tags.Multiplication(1), Tags.Multiplication(2), Tags.Multiplication(3)))).run
                                                  //> res4: scalaz.Scalaz.Id[scalaz.@@[Int,scalaz.Tags.Multiplication]] = 6


  // drop returns Unit... but it can be composed with other Iteratee's monadically...
  (drop[Int, Id](1) &= enumerate(Stream(1, 2, 3))).run
  
  def drop1keep1[E]: Iteratee[E, Option[E]] = for {
    _ <- drop[E, Id](1)
    x <- head[E, Id]
  } yield x                                       //> drop1keep1: [E]=> scalaz.iteratee.Iteratee[E,Option[E]]
  
  (drop1keep1[Int] &= enumerate(Stream(1, 2, 3))).run
                                                  //> res5: scalaz.Id.Id[Option[Int]] = Some(2)
  // looks nicer without the brackets...
  drop1keep1[Int] &= enumerate(Stream(1, 2, 3)) run
                                                  //> res6: scalaz.Id.Id[Option[Int]] = Some(2)
  
  // here's the flatmap way... I think the for comprehension is clearer!
  drop[Int, Id](1) flatMap { Unit => head[Int, Id] } &= enumerate(Stream(1, 2, 3)) run
                                                  //> res7: scalaz.Scalaz.Id[Option[Int]] = Some(2)
  
  
  // File input with Iteratees.
  import java.io._
  import effect._
  
  val er = enumReader[IO](new BufferedReader(new FileReader("eclipse.ini")))
                                                  //> er  : scalaz.iteratee.EnumeratorT[scalaz.effect.IoExceptionOr[Char],scalaz.
                                                  //| effect.IO] = scalaz.iteratee.EnumeratorTFunctions$$anon$15@183ff528
  
  (head[IoExceptionOr[Char], IO] &= er).map(_ flatMap {_.toOption}).run.unsafePerformIO
                                                  //> res8: Option[Char] = Some(-)
  
  
  // We can get the number of lines in two files combined, by composing two
  // enumerations and using our "counter" iteratee from above...
  
  def lengthOfTwoFiles(f1: File, f2: File) = {
    val l1 = length[IoExceptionOr[Char], IO] &= enumReader[IO](new BufferedReader(new FileReader(f1)))
    val l2 = l1 &= enumReader[IO](new BufferedReader(new FileReader(f2)))
    l2.run
  }                                               //> lengthOfTwoFiles: (f1: java.io.File, f2: java.io.File)scalaz.effect.IO[Int]
                                                  //| 
  lengthOfTwoFiles(new File("eclipse.ini"), new File("notice.html")).unsafePerformIO
                                                  //> res9: Int = 9493
  
  // more examples...
  val readLn = takeWhile[Char, List](_ != '\n') flatMap (ln => drop[Char, Id](1).map(_ => ln))
                                                  //> readLn  : scalaz.iteratee.IterateeT[Char,scalaz.Id.Id,List[Char]] = scalaz.
                                                  //| iteratee.IterateeTFunctions$$anon$9@12944313
  (readLn &= enumStream("Iteratees\nare\ncomposable".toStream)).run
                                                  //> res10: scalaz.Id.Id[List[Char]] = List(I, t, e, r, a, t, e, e, s)
  (collect[List[Char], List] %= readLn.sequenceI &= enumStream("Iteratees\nare\ncomposable".toStream)).run
                                                  //> res11: scalaz.Id.Id[List[List[Char]]] = List(List(I, t, e, r, a, t, e, e, s
                                                  //| ), List(a, r, e), List(c, o, m, p, o, s, a, b, l, e))
}