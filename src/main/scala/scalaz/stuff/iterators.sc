package scalaz.stuff

import scalaz._
import Scalaz._

object iterators {

  // The option operator is injected to Boolean, which expands (x > 0) option (x + 1) to
  // if (x > 0) Some(x + 1) else None.
  
  List(1, 2, 3) traverse { x => (x > 0) option (x + 1) }
                                                  //> res0: Option[List[Int]] = Some(List(2, 3, 4))
  List(1, 2, 0) traverse { x => (x > 0) option (x + 1) }
                                                  //> res1: Option[List[Int]] = None
  
  Monoid[Int].applicative.traverse(List(1, 2, 3)) { _ + 1 }
                                                  //> res2: Int = 9
  
  
  // Now we can take any data structure that supports Traverse and turn it into a List.
  
  def contents[F[_]: Traverse, A](f: F[A]): List[A] =
    Monoid[List[A]].applicative.traverse(f) { List(_) }
                                                  //> contents: [F[_], A](f: F[A])(implicit evidence$2: scalaz.Traverse[F])List[A]
                                                  //| 
    
  contents(List(1, 2, 3))                         //> res3: List[Int] = List(1, 2, 3)
  contents(NonEmptyList(1, 2, 3))                 //> res4: List[Int] = List(1, 2, 3)
  
  val tree: Tree[Char] = 'P'.node('O'.leaf, 'L'.leaf)
                                                  //> tree  : scalaz.Tree[Char] = <tree>
  contents(tree)                                  //> res5: List[Char] = List(P, O, L)
  
  
  def shape[F[_]: Traverse, A](f: F[A]): F[Unit] =
    f traverse {_ => ((): Id[Unit])}              //> shape: [F[_], A](f: F[A])(implicit evidence$3: scalaz.Traverse[F])F[Unit]
    
  shape(List(1, 2, 3))                            //> res6: List[Unit] = List((), (), ())
  shape(tree).drawTree                            //> res7: String = "()
                                                  //| |
                                                  //| +- ()
                                                  //| |
                                                  //| `- ()
                                                  //| "
  
  
  def decompose[F[_]: Traverse, A](f: F[A]) =
    Applicative[Id].product[({type l[X]=List[A]})#l].traverse(f) { x => (((): Id[Unit]), List(x)) }
                                                  //> decompose: [F[_], A](f: F[A])(implicit evidence$4: scalaz.Traverse[F])(scal
                                                  //| az.Scalaz.Id[F[Unit]], List[A])
  decompose(List(1, 2, 3, 4))                     //> res8: (scalaz.Scalaz.Id[List[Unit]], List[Int]) = (List((), (), (), ()),Lis
                                                  //| t(1, 2, 3, 4))
  decompose(tree)                                 //> res9: (scalaz.Scalaz.Id[scalaz.Tree[Unit]], List[Char]) = (<tree>,List(P, O
                                                  //| , L))
  
  
  // Sequence
  List(1.some, 2.some).sequence                   //> res10: Option[List[Int]] = Some(List(1, 2))
  List(1.some, 2.some, none).sequence             //> res11: Option[List[Int]] = None
  
  val validationTree: Tree[Validation[String, Int]] = 1.success[String].node(
    2.success[String].leaf, 3.success[String].leaf)
                                                  //> validationTree  : scalaz.Tree[scalaz.Validation[String,Int]] = <tree>
  
  validationTree.sequence[({ type l[X]=Validation[String, X] })#l, Int]
                                                  //> res12: scalaz.Validation[String,scalaz.Tree[Int]] = Success(<tree>)
  val failedTree: Tree[Validation[String, Int]] = 1.success[String].node(
    2.success[String].leaf, "boom".failure[Int].leaf)
                                                  //> failedTree  : scalaz.Tree[scalaz.Validation[String,Int]] = <tree>
  
  failedTree.sequence[({ type l[X]=Validation[String, X] })#l, Int]
                                                  //> res13: scalaz.Validation[String,scalaz.Tree[Int]] = Failure(boom)
  
  // Using Unapply (provided by the *U functions) - much nicer than seeing the ugly type-lambda - see more below:
  failedTree.sequenceU                            //> res14: scalaz.Validation[String,scalaz.Tree[Int]] = Failure(boom)
  
  
  
  // TYPE LAMBDAS AND UNAPPLY => See the worksheet on Unapply.
  
  def sequenceList[F[_]: Applicative, A](xs: List[F[A]]): F[List[A]] =
    xs.foldRight(List.empty[A].point[F])((a, b) => ^(a, b)(_ :: _))
                                                  //> sequenceList: [F[_], A](xs: List[F[A]])(implicit evidence$5: scalaz.Applica
                                                  //| tive[F])F[List[A]]
  
  sequenceList(List(some(1),some(2)))             //> res15: Option[List[Int]] = Some(List(1, 2))
  sequenceList(List(some(1),none))                //> res16: Option[List[Int]] = None
  
  
  // The scala compiler is unable to work out the below types - we can use a type lambda to get around it:
  
  //sequenceList(List(\/.right(42), \/.left(NonEmptyList("oops"))))
  sequenceList[({type l[A] = NonEmptyList[String] \/ A})#l, Int](List(\/.right(42), \/.left(NonEmptyList("oops"))))
                                                  //> res17: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = -\/(NonEmptyList(
                                                  //| oops))
}