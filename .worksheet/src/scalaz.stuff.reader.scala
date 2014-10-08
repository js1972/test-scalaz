package scalaz.stuff

import scalaz._
import Scalaz._

object reader {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(227); 
  
  // MONAD TRANSFORMER STUFF IS BELOW...
  
  
  // scalaz makes these instances of Functor so when we map - it composes functions
  val f = (_: Int) * 5;System.out.println("""f  : Int => Int = """ + $show(f ));$skip(23); 
  val g = (_: Int) + 3;System.out.println("""g  : Int => Int = """ + $show(g ));$skip(91); val res$0 = 
  
  // as you can see below, with map, the function on the left is called first
  g map f;System.out.println("""res0: Int => Int = """ + $show(res$0));$skip(15); val res$1 = 
  (g map f)(8);System.out.println("""res1: Int = """ + $show(res$1));$skip(15); val res$2 = 
  (f map g)(8);System.out.println("""res2: Int = """ + $show(res$2));$skip(109); val res$3 = 
  
  // with compose the function on the right is called first - looks more natural to me
  (f compose g)(8);System.out.println("""res3: Int = """ + $show(res$3));$skip(19); val res$4 = 
  (g compose f)(8);System.out.println("""res4: Int = """ + $show(res$4));$skip(221); 
  
  
  // scalaz also makes functions applicative functors - allows us to operate on
  // the eventuial results of functions as if we already had their results
  val func = (((_: Int) * 2) |@| ((_: Int) + 10)) { _ + _ };System.out.println("""func  : Int => Int = """ + $show(func ));$skip(10); val res$5 = 
  func(3);System.out.println("""res5: Int = """ + $show(res$5));$skip(280); 
  
  
  
  // Not only is function a functor and an applicative functor thanks to scalaz,
  // but its also a monad.
  // The Reader Monad is basically just using functions as monads.
  val addStuff: Int => Int = for {
    a <- (_: Int) * 2
    b <- (_: Int) + 10
  } yield a + b;System.out.println("""addStuff  : Int => Int = """ + $show(addStuff ));$skip(17); val res$6 = 
  
  addStuff(3);System.out.println("""res6: Int = """ + $show(res$6));$skip(145); 
  
  // Re-written:
  val addStuff2: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b;System.out.println("""addStuff2  : scalaz.Reader[Int,Int] = """ + $show(addStuff2 ));$skip(18); val res$7 = 
  
  addStuff2(3);System.out.println("""res7: scalaz.Id.Id[Int] = """ + $show(res$7));$skip(266); 
  
  
  // Example ues of Reader
  // The point of the Reader Monad is to pass in the configuration information
  // once and everyone uses it without explicitly passing it around.
  def myName(step: String): Reader[String, String] = Reader { step + ", I am " + _ };System.out.println("""myName: (step: String)scalaz.Reader[String,String]""");$skip(189); 
  def localExample: Reader[String, (String, String, String)] = for {
    a <- myName("First")
    b <- myName("Second") >=> Reader { _ + "dy" }
    c <- myName("Third")
  } yield (a, b, c);System.out.println("""localExample: => scalaz.Reader[String,(String, String, String)]""");$skip(26); val res$8 = 
  
  localExample("Fred");System.out.println("""res8: scalaz.Id.Id[(String, String, String)] = """ + $show(res$8));$skip(308); 
  
  
  
  
  
  // Some more simple examples...
  // from https://www.evernote.com/shard/s4/sh/9f3b79df-229d-48fc-9758-5cfc38ab0664/94410496b7179081346b551e647837c0
  
  // Here we compose some functions. Scalas Function1 lets us compose
  // new functions using andThen
  
  val triple = (i: Int) => i * 3;System.out.println("""triple  : Int => Int = """ + $show(triple ));$skip(12); val res$9 = 
  triple(3);System.out.println("""res9: Int = """ + $show(res$9));$skip(51); 
  
  val thricePlus2 = triple andThen (i => i + 2);System.out.println("""thricePlus2  : Int => Int = """ + $show(thricePlus2 ));$skip(17); val res$10 = 
  thricePlus2(3);System.out.println("""res10: Int = """ + $show(res$10));$skip(56); 
  
  val myfunc = thricePlus2 andThen (i => i.toString);System.out.println("""myfunc  : Int => String = """ + $show(myfunc ));$skip(12); val res$11 = 
  myfunc(3);System.out.println("""res11: String = """ + $show(res$11));$skip(194); 
  
  // The Reader Monad is a monad defined for unary functions,
  // using andThen as the map operation. A Reader, then, is
  // just a Function1.
  
  val triple_2 = Reader((i: Int) => i * 3);System.out.println("""triple_2  : scalaz.Reader[Int,Int] = """ + $show(triple_2 ));$skip(14); val res$12 = 
  triple_2(3);System.out.println("""res12: scalaz.Id.Id[Int] = """ + $show(res$12));$skip(51); 
  
  val thricePlus2_2 = triple_2 map (i => i + 2);System.out.println("""thricePlus2_2  : scalaz.Kleisli[scalaz.Id.Id,Int,Int] = """ + $show(thricePlus2_2 ));$skip(19); val res$13 = 
  thricePlus2_2(3);System.out.println("""res13: scalaz.Id.Id[Int] = """ + $show(res$13));$skip(144); 
  
  // The map and flatMap methods let us use for comprehensions to define new Readers:
  
  val f_3 = for (i <- thricePlus2) yield i.toString;System.out.println("""f_3  : Int => String = """ + $show(f_3 ));$skip(9); val res$14 = 
  f_3(3)
  
  
  
  
  // Example use of a Monad Transformer: ReaderT to combine a Reader with Option,
  // which gives us failure handling in a Reader ->
  
  type ReaderTOption[A, B] = ReaderT[Option, A, B]
  //object ReaderTOption extends KleisliFunctions with KleisliInstances {
  object ReaderTOption extends KleisliInstances with KleisliFunctions {
    def apply[A, B](f: A => Option[B]): ReaderTOption[A, B] = kleisli(f)
  };System.out.println("""res14: String = """ + $show(res$14));$skip(515); 
  
  def configure(key: String) = ReaderTOption[Map[String, String], String] { _.get(key) };System.out.println("""configure: (key: String)scalaz.stuff.reader.ReaderTOption[Map[String,String],String]""");$skip(164); 
  
  def setupConnection = for {
    host <- configure("host")
    user <- configure("user")
    password <- configure("password")
  } yield (host, user, password);System.out.println("""setupConnection: => scalaz.Kleisli[Option,Map[String,String],(String, String, String)]""");$skip(103); 
  val goodConfig = Map(
    "host" -> "eed3si9n.com",
    "user" -> "sa",
    "password" -> "****"
  );System.out.println("""goodConfig  : scala.collection.immutable.Map[String,String] = """ + $show(goodConfig ));$skip(33); val res$15 = 
  
  setupConnection(goodConfig);System.out.println("""res15: Option[(String, String, String)] = """ + $show(res$15));$skip(78); 
  
  val badConfig = Map(
    "host" -> "example.com",
    "user" -> "sa"
  );System.out.println("""badConfig  : scala.collection.immutable.Map[String,String] = """ + $show(badConfig ));$skip(32); val res$16 = 
  
  setupConnection(badConfig)
  
  
  // We can continue to stack monads. Here we will stack StateT to represent state transfer
  // on top of ReaderTOption. We'll re-work the stack example from state_monad.sc.
  //
  // For some reason this worksheet cannot see the ReaderTOption type defined above, so
  // I've had to redefine it here as ReaderTOption2.
  // Also it does not like the covariant type X as specified in
  // http://eed3si9n.com/learning-scalaz/Monad+transformers.html so I've removed them.
  //
  // This is pretty painful stuff to setup! Must be better ways?!?
  
  type ReaderTOption2[A, B] = ReaderT[Option, A, B]
  object ReaderTOption2 extends KleisliInstances with KleisliFunctions {
    def apply[A, B](f: A => Option[B]): ReaderTOption2[A, B] = kleisli(f)
  }
  
  type StateTReaderTOption[C, S, A] = StateT[({ type l[X] = ReaderTOption2[C, X] })#l, S, A]
  
  object StateTReaderTOption extends StateTInstances with StateTFunctions {
    def apply[C, S, A](f: S => (S, A)) = new StateT[({ type l[X] = ReaderTOption2[C, X] })#l, S, A] {
      def apply(s: S) = f(s).point[({ type l[X] = ReaderTOption2[C, X] })#l]
    }
  
    def get[C, S]: StateTReaderTOption[C, S, S] =
      StateTReaderTOption { s => (s, s) }
    def put[C, S](s: S): StateTReaderTOption[C, S, Unit] =
      StateTReaderTOption { _ => (s, ()) }
  }
  
  
  type Stack = List[Int]
  type Config = Map[String, String];System.out.println("""res16: Option[(String, String, String)] = """ + $show(res$16));$skip(1585); 
  
  val pop: StateTReaderTOption[Config, Stack, Int] = {
    import StateTReaderTOption.{get, put}
    for {
      s <- get[Config, Stack]
      val (x :: xs) = s
      _ <- put(xs)
    } yield x
  };System.out.println("""pop  : scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,scalaz.stuff.reader.Stack,Int] = """ + $show(pop ));$skip(190); 
  def push(x: Int): StateTReaderTOption[Config, Stack, Unit] = {
    import StateTReaderTOption.{get, put}
    for {
      xs <- get[Config, Stack]
      r <- put(x :: xs)
    } yield r
  };System.out.println("""push: (x: Int)scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,scalaz.stuff.reader.Stack,Unit]""");$skip(121); 
  def stackManip: StateTReaderTOption[Config, Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield b;System.out.println("""stackManip: => scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,scalaz.stuff.reader.Stack,Int]""");$skip(41); val res$17 = 
  
  stackManip(List(5, 8, 2, 1))(Map());System.out.println("""res17: Option[(scalaz.stuff.reader.Stack, Int)] = """ + $show(res$17));$skip(229); 
  
  
  // Lets modify configure...
  def configure2[S](key: String) = new StateTReaderTOption[Config, S, String] {
    def apply(s: S) = ReaderTOption2[Config, (S, String)] { config: Config => config.get(key) map {(s, _)} }
  };System.out.println("""configure2: [S](key: String)scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,S,String]""");$skip(131); 
  
  def stackManip2: StateTReaderTOption[Config, Stack, Unit] = for {
    x <- configure2("x")
    a <- push(x.toInt)
  } yield a;System.out.println("""stackManip2: => scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,scalaz.stuff.reader.Stack,Unit]""");$skip(52); val res$18 = 
  
  stackManip2(List(5, 8, 2, 1))(Map("x" -> "7"));System.out.println("""res18: Option[(scalaz.stuff.reader.Stack, Unit)] = """ + $show(res$18));$skip(49); val res$19 = 
  stackManip2(List(5, 8, 2, 1))(Map("y" -> "7"));System.out.println("""res19: Option[(scalaz.stuff.reader.Stack, Unit)] = """ + $show(res$19))}
}
