package scalaz.stuff

import scalaz._
import Scalaz._

object reader {
  
  // MONAD TRANSFORMER STUFF IS BELOW...
  
  
  // scalaz makes these instances of Functor so when we map - it composes functions
  val f = (_: Int) * 5                            //> f  : Int => Int = <function1>
  val g = (_: Int) + 3                            //> g  : Int => Int = <function1>
  
  // as you can see below, with map, the function on the left is called first
  g map f                                         //> res0: Int => Int = <function1>
  (g map f)(8)                                    //> res1: Int = 55
  (f map g)(8)                                    //> res2: Int = 43
  
  // with compose the function on the right is called first - looks more natural to me
  (f compose g)(8)                                //> res3: Int = 55
  (g compose f)(8)                                //> res4: Int = 43
  
  
  // scalaz also makes functions applicative functors - allows us to operate on
  // the eventuial results of functions as if we already had their results
  val func = (((_: Int) * 2) |@| ((_: Int) + 10)) { _ + _ }
                                                  //> func  : Int => Int = <function1>
  func(3)                                         //> res5: Int = 19
  
  
  
  // Not only is function a functor and an applicative functor thanks to scalaz,
  // but its also a monad.
  // The Reader Monad is basically just using functions as monads.
  val addStuff: Int => Int = for {
    a <- (_: Int) * 2
    b <- (_: Int) + 10
  } yield a + b                                   //> addStuff  : Int => Int = <function1>
  
  addStuff(3)                                     //> res6: Int = 19
  
  // Re-written:
  val addStuff2: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b                                   //> addStuff2  : scalaz.Reader[Int,Int] = Kleisli(<function1>)
  
  addStuff2(3)                                    //> res7: scalaz.Id.Id[Int] = 19
  
  
  // Example ues of Reader
  // The point of the Reader Monad is to pass in the configuration information
  // once and everyone uses it without explicitly passing it around.
  def myName(step: String): Reader[String, String] = Reader { step + ", I am " + _ }
                                                  //> myName: (step: String)scalaz.Reader[String,String]
  def localExample: Reader[String, (String, String, String)] = for {
    a <- myName("First")
    b <- myName("Second") >=> Reader { _ + "dy" }
    c <- myName("Third")
  } yield (a, b, c)                               //> localExample: => scalaz.Reader[String,(String, String, String)]
  
  localExample("Fred")                            //> res8: scalaz.Id.Id[(String, String, String)] = (First, I am Fred,Second, I 
                                                  //| am Freddy,Third, I am Fred)
  
  
  
  
  
  // Some more simple examples...
  // from https://www.evernote.com/shard/s4/sh/9f3b79df-229d-48fc-9758-5cfc38ab0664/94410496b7179081346b551e647837c0
  
  // Here we compose some functions. Scalas Function1 lets us compose
  // new functions using andThen
  
  val triple = (i: Int) => i * 3                  //> triple  : Int => Int = <function1>
  triple(3)                                       //> res9: Int = 9
  
  val thricePlus2 = triple andThen (i => i + 2)   //> thricePlus2  : Int => Int = <function1>
  thricePlus2(3)                                  //> res10: Int = 11
  
  val myfunc = thricePlus2 andThen (i => i.toString)
                                                  //> myfunc  : Int => String = <function1>
  myfunc(3)                                       //> res11: String = 11
  
  // The Reader Monad is a monad defined for unary functions,
  // using andThen as the map operation. A Reader, then, is
  // just a Function1.
  
  val triple_2 = Reader((i: Int) => i * 3)        //> triple_2  : scalaz.Reader[Int,Int] = Kleisli(<function1>)
  triple_2(3)                                     //> res12: scalaz.Id.Id[Int] = 9
  
  val thricePlus2_2 = triple_2 map (i => i + 2)   //> thricePlus2_2  : scalaz.Kleisli[scalaz.Id.Id,Int,Int] = Kleisli(<function1>
                                                  //| )
  thricePlus2_2(3)                                //> res13: scalaz.Id.Id[Int] = 11
  
  // The map and flatMap methods let us use for comprehensions to define new Readers:
  
  val f_3 = for (i <- thricePlus2) yield i.toString
                                                  //> f_3  : Int => String = <function1>
  f_3(3)                                          //> res14: String = 11
  
  
  
  
  // Example use of a Monad Transformer: ReaderT to combine a Reader with Option,
  // which gives us failure handling in a Reader ->
  
  type ReaderTOption[A, B] = ReaderT[Option, A, B]
  //object ReaderTOption extends KleisliFunctions with KleisliInstances {
  object ReaderTOption extends KleisliInstances with KleisliFunctions {
    def apply[A, B](f: A => Option[B]): ReaderTOption[A, B] = kleisli(f)
  }
  
  def configure(key: String) = ReaderTOption[Map[String, String], String] { _.get(key) }
                                                  //> configure: (key: String)scalaz.stuff.reader.ReaderTOption[Map[String,String
                                                  //| ],String]
  
  def setupConnection = for {
    host <- configure("host")
    user <- configure("user")
    password <- configure("password")
  } yield (host, user, password)                  //> setupConnection: => scalaz.Kleisli[Option,Map[String,String],(String, Strin
                                                  //| g, String)]
  val goodConfig = Map(
    "host" -> "eed3si9n.com",
    "user" -> "sa",
    "password" -> "****"
  )                                               //> goodConfig  : scala.collection.immutable.Map[String,String] = Map(host -> e
                                                  //| ed3si9n.com, user -> sa, password -> ****)
  
  setupConnection(goodConfig)                     //> res15: Option[(String, String, String)] = Some((eed3si9n.com,sa,****))
  
  val badConfig = Map(
    "host" -> "example.com",
    "user" -> "sa"
  )                                               //> badConfig  : scala.collection.immutable.Map[String,String] = Map(host -> ex
                                                  //| ample.com, user -> sa)
  
  setupConnection(badConfig)                      //> res16: Option[(String, String, String)] = None
  
  
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
  type Config = Map[String, String]
  
  val pop: StateTReaderTOption[Config, Stack, Int] = {
    import StateTReaderTOption.{get, put}
    for {
      s <- get[Config, Stack]
      val (x :: xs) = s
      _ <- put(xs)
    } yield x
  }                                               //> pop  : scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.Config,s
                                                  //| calaz.stuff.reader.Stack,Int] = scalaz.IndexedStateT$$anon$10@4f902864
  def push(x: Int): StateTReaderTOption[Config, Stack, Unit] = {
    import StateTReaderTOption.{get, put}
    for {
      xs <- get[Config, Stack]
      r <- put(x :: xs)
    } yield r
  }                                               //> push: (x: Int)scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.C
                                                  //| onfig,scalaz.stuff.reader.Stack,Unit]
  def stackManip: StateTReaderTOption[Config, Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield b                                       //> stackManip: => scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader.
                                                  //| Config,scalaz.stuff.reader.Stack,Int]
  
  stackManip(List(5, 8, 2, 1))(Map())             //> res17: Option[(scalaz.stuff.reader.Stack, Int)] = Some((List(8, 2, 1),5))
  
  
  // Lets modify configure...
  def configure2[S](key: String) = new StateTReaderTOption[Config, S, String] {
    def apply(s: S) = ReaderTOption2[Config, (S, String)] { config: Config => config.get(key) map {(s, _)} }
  }                                               //> configure2: [S](key: String)scalaz.stuff.reader.StateTReaderTOption[scalaz.
                                                  //| stuff.reader.Config,S,String]
  
  def stackManip2: StateTReaderTOption[Config, Stack, Unit] = for {
    x <- configure2("x")
    a <- push(x.toInt)
  } yield a                                       //> stackManip2: => scalaz.stuff.reader.StateTReaderTOption[scalaz.stuff.reader
                                                  //| .Config,scalaz.stuff.reader.Stack,Unit]
  
  stackManip2(List(5, 8, 2, 1))(Map("x" -> "7"))  //> res18: Option[(scalaz.stuff.reader.Stack, Unit)] = Some((List(7, 5, 8, 2, 1
                                                  //| ),()))
  stackManip2(List(5, 8, 2, 1))(Map("y" -> "7"))  //> res19: Option[(scalaz.stuff.reader.Stack, Unit)] = None
}