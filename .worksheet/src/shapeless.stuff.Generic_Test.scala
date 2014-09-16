package shapeless.stuff

import shapeless._
import poly._

/**
 * Generic gives us a way to convert between HLists and case classes.
 *
 */
object Generic_Test {
  case class Foo(i: Int, s: String, b: Boolean);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(240); 
  
  val fooGen = Generic[Foo];System.out.println("""fooGen  : shapeless.Generic[shapeless.stuff.Generic_Test.Foo]{type Repr = shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]]} = """ + $show(fooGen ));$skip(36); 
  
  val foo = Foo(23, "foo", true);System.out.println("""foo  : shapeless.stuff.Generic_Test.Foo = """ + $show(foo ));$skip(29); 
  
  val h1 = fooGen.to(foo);System.out.println("""h1  : shapeless.stuff.Generic_Test.fooGen.Repr = """ + $show(h1 ));$skip(28); 
  
  val h2 = 13 :: h1.tail;System.out.println("""h2  : shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]] = """ + $show(h2 ));$skip(18); val res$0 = 
  fooGen.from(h2)
  
  
  
  // Simple recursive case class family
  sealed trait Tree[T]
  case class Leaf[T](t: T) extends Tree[T]
  case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
  
  // Polymorphic function which adds 1 to any Int and is the identity
  // on all other values
  // -> is a class that lifts a Function1 to a Poly1.
  object inc extends ->((i: Int) => i+1);System.out.println("""res0: shapeless.stuff.Generic_Test.Foo = """ + $show(res$0));$skip(622); 
  
  val tree: Tree[Int] =
    Node(
      Node(
        Node(
          Leaf(1),
          Node(
            Leaf(2),
            Leaf(3)
          )
        ),
        Leaf(4)
      ),
      Node(
        Leaf(5),
        Leaf(6)
      )
    );System.out.println("""tree  : shapeless.stuff.Generic_Test.Tree[Int] = """ + $show(tree ));$skip(144); val res$1 = 
  // Transform tree by applying inc everywhere
  // !! Note - does not work in scala worksheet - worksd fine in REPL !!
  everywhere(inc)(tree);System.out.println("""res1: <error> = """ + $show(res$1))}
  
  
  
  import record._, syntax.singleton._
 
  case class Book(author: String, title: String, id: Int, price: Double)
  
  // Further stuff which internally uses macros just does not work in in a scala worksheet !!
  //val bookGen = LabelledGeneric[Book]
  
  //val tapl = Book("Benjamin Pierce", "Types and Programming Languages", 262162091, 44.11)
}
