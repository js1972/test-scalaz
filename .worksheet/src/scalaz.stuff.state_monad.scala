package scalaz.stuff

import scalaz._
import Scalaz._

object state_monad {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(623); val res$0 = 

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
  
  
  // Implementation of the stack again but with a State Monad
  // Note that there is now no need to pass around the state values and
  // we can monadically chain operations in the for syntax.
  
  // State monad encapsulates functions that take a state and return a pair
  // of a value and a state.
  
  type Stack = List[Int];System.out.println("""res0: scalaz.State[List[Int],Int] = """ + $show(res$0));$skip(393); 
  val pop = State[Stack, Int] { case x :: xs => (xs, x)};System.out.println("""pop  : scalaz.State[scalaz.stuff.state_monad.Stack,Int] = """ + $show(pop ));$skip(69); 
  def push(a: Int) = State[Stack, Unit] { case xs => (a :: xs, ()) };System.out.println("""push: (a: Int)scalaz.State[scalaz.stuff.state_monad.Stack,Unit]""");$skip(102); 
  
  def stackManip: State[Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield b;System.out.println("""stackManip: => scalaz.State[scalaz.stuff.state_monad.Stack,Int]""");$skip(34); val res$1 = 
  
  stackManip(List(5, 8, 2, 1));System.out.println("""res1: scalaz.Id.Id[(scalaz.stuff.state_monad.Stack, Int)] = """ + $show(res$1));$skip(177); 
  
  
  def stackyStack: State[Stack, Unit] = for {
    stackNow <- get
    r <- if (stackNow === List(1, 2, 3)) put(List(8, 3, 1))
         else put(List(9, 2, 1))
  } yield r;System.out.println("""stackyStack: => scalaz.State[scalaz.stuff.state_monad.Stack,Unit]""");$skip(32); val res$2 = 
  
  stackyStack(List(1, 2, 3));System.out.println("""res2: scalaz.Id.Id[(scalaz.stuff.state_monad.Stack, Unit)] = """ + $show(res$2));$skip(170); 
  
  
  // Implement push and pop in terms of get and put...
  val pop2: State[Stack, Int] = for {
    s <- get[Stack]
    val (x :: xs) = s
    _ <- put(xs)
  } yield x;System.out.println("""pop2  : scalaz.State[scalaz.stuff.state_monad.Stack,Int] = """ + $show(pop2 ));$skip(106); 
  
  def push2(x: Int): State[Stack, Unit] = for {
    xs <- get[Stack]
    r <- put(x :: xs)
  } yield r;System.out.println("""push2: (x: Int)scalaz.State[scalaz.stuff.state_monad.Stack,Unit]""")}
  
  // See reader.sc for example of using a monad transformer for this stack example...
}
