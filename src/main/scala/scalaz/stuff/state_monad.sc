package scalaz.stuff

import scalaz._
import Scalaz._

object state_monad {

  // Implementation of a stack in scala
  //type Stack = List[Int]
  //
  //def pop(stack: Stack): (Int, Stack) = stack match {
  //  case x :: xs => (x, xs)
  //}
  //def push(a: Int, stack: Stack): (Unit, Stack) = ((), a :: stack)
  //
  //def stackManip(stack: Stack): (Int, Stack) = {
  //  val (_, newStack1) = push(3, stack)
  //  val (a, newStack2) = pop(newStack1)
  //  pop(newStack2)
  //}
  //
  //stackManip(List(5, 8, 2, 1))
  
  
  // ScalaZ State monad - construct with singleton
  State[List[Int], Int] { case x :: xs => (xs, x) }
                                                  //> res0: scalaz.State[List[Int],Int] = scalaz.package$State$$anon$3@4d4acd0b
  
  
  // Implementation of the stack again but with a State Monad
  // Note that there is now no need to pass around the state values and
  // we can monadically chain operations in the for syntax.
  
  // State monad encapsulates functions that take a state and return a pair
  // of a value and a state.
  
  type Stack = List[Int]
  val pop = State[Stack, Int] { case x :: xs => (xs, x)}
                                                  //> pop  : scalaz.State[scalaz.stuff.state_monad.Stack,Int] = scalaz.package$St
                                                  //| ate$$anon$3@1a74681a
  def push(a: Int) = State[Stack, Unit] { case xs => (a :: xs, ()) }
                                                  //> push: (a: Int)scalaz.State[scalaz.stuff.state_monad.Stack,Unit]
  
  def stackManip: State[Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield b                                       //> stackManip: => scalaz.State[scalaz.stuff.state_monad.Stack,Int]
  
  stackManip(List(5, 8, 2, 1))                    //> res1: scalaz.Id.Id[(scalaz.stuff.state_monad.Stack, Int)] = (List(8, 2, 1),
                                                  //| 5)
  
  
  def stackyStack: State[Stack, Unit] = for {
    stackNow <- get
    r <- if (stackNow === List(1, 2, 3)) put(List(8, 3, 1))
         else put(List(9, 2, 1))
  } yield r                                       //> stackyStack: => scalaz.State[scalaz.stuff.state_monad.Stack,Unit]
  
  stackyStack(List(1, 2, 3))                      //> res2: scalaz.Id.Id[(scalaz.stuff.state_monad.Stack, Unit)] = (List(8, 3, 1)
                                                  //| ,())
  
  
  // Implement push and pop in terms of get and put...
  val pop2: State[Stack, Int] = for {
    s <- get[Stack]
    val (x :: xs) = s
    _ <- put(xs)
  } yield x                                       //> pop2  : scalaz.State[scalaz.stuff.state_monad.Stack,Int] = scalaz.IndexedSt
                                                  //| ateT$$anon$10@21f182d2
  
  def push2(x: Int): State[Stack, Unit] = for {
    xs <- get[Stack]
    r <- put(x :: xs)
  } yield r                                       //> push2: (x: Int)scalaz.State[scalaz.stuff.state_monad.Stack,Unit]
  
  // See reader.sc for example of using a monad transformer for this stack example...
}