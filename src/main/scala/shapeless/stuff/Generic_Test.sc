package shapeless.stuff

import shapeless._
import poly._

/**
 * Generic gives us a way to convert between HLists and case classes.
 *
 */
object Generic_Test {
  case class Foo(i: Int, s: String, b: Boolean)
  
  val fooGen = Generic[Foo]                       //> fooGen  : shapeless.Generic[shapeless.stuff.Generic_Test.Foo]{type Repr = sh
                                                  //| apeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]]} =
                                                  //|  shapeless.stuff.Generic_Test$$anonfun$main$1$fresh$macro$9$1@e0340d1
  
  val foo = Foo(23, "foo", true)                  //> foo  : shapeless.stuff.Generic_Test.Foo = Foo(23,foo,true)
  
  val h1 = fooGen.to(foo)                         //> h1  : shapeless.stuff.Generic_Test.fooGen.Repr = 23 :: foo :: true :: HNil
  
  val h2 = 13 :: h1.tail                          //> h2  : shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HN
                                                  //| il]]] = 13 :: foo :: true :: HNil
  fooGen.from(h2)                                 //> res0: shapeless.stuff.Generic_Test.Foo = Foo(13,foo,true)
  
  
  
  // Simple recursive case class family
  sealed trait Tree[T]
  case class Leaf[T](t: T) extends Tree[T]
  case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
  
  // Polymorphic function which adds 1 to any Int and is the identity
  // on all other values
  // -> is a class that lifts a Function1 to a Poly1.
  object inc extends ->((i: Int) => i+1)
  
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
    )                                             //> tree  : shapeless.stuff.Generic_Test.Tree[Int] = Node(Node(Node(Leaf(1),Node
                                                  //| (Leaf(2),Leaf(3))),Leaf(4)),Node(Leaf(5),Leaf(6)))
  // Transform tree by applying inc everywhere
  // !! Note - does not work in scala worksheet - worksd fine in REPL !!
  everywhere(inc)(tree)                           //> res1: <error> = Node(Node(Node(Leaf(1),Node(Leaf(2),Leaf(3))),Leaf(4)),Node
                                                  //| (Leaf(5),Leaf(6)))
  
  
  
  import record._, syntax.singleton._
 
  case class Book(author: String, title: String, id: Int, price: Double)
  
  // Further stuff which internally uses macros just does not work in in a scala worksheet !!
  //val bookGen = LabelledGeneric[Book]
  
  //val tapl = Book("Benjamin Pierce", "Types and Programming Languages", 262162091, 44.11)
}