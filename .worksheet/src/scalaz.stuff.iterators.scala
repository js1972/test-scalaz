package scalaz.stuff

import scalaz._
import Scalaz._

object iterators {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(262); val res$0 = 

  // The option operator is injected to Boolean, which expands (x > 0) option (x + 1) to
  // if (x > 0) Some(x + 1) else None.
  
  List(1, 2, 3) traverse { x => (x > 0) option (x + 1) };System.out.println("""res0: Option[List[Int]] = """ + $show(res$0));$skip(57); val res$1 = 
  List(1, 2, 0) traverse { x => (x > 0) option (x + 1) };System.out.println("""res1: Option[List[Int]] = """ + $show(res$1));$skip(63); val res$2 = 
  
  Monoid[Int].applicative.traverse(List(1, 2, 3)) { _ + 1 };System.out.println("""res2: Int = """ + $show(res$2));$skip(207); 
  
  
  // Now we can take any data structure that supports Traverse and turn it into a List.
  
  def contents[F[_]: Traverse, A](f: F[A]): List[A] =
    Monoid[List[A]].applicative.traverse(f) { List(_) };System.out.println("""contents: [F[_], A](f: F[A])(implicit evidence$2: scalaz.Traverse[F])List[A]""");$skip(31); val res$3 = 
    
  contents(List(1, 2, 3));System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(34); val res$4 = 
  contents(NonEmptyList(1, 2, 3));System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(58); 
  
  val tree: Tree[Char] = 'P'.node('O'.leaf, 'L'.leaf);System.out.println("""tree  : scalaz.Tree[Char] = """ + $show(tree ));$skip(17); val res$5 = 
  contents(tree);System.out.println("""res5: List[Char] = """ + $show(res$5));$skip(94); 
  
  
  def shape[F[_]: Traverse, A](f: F[A]): F[Unit] =
    f traverse {_ => ((): Id[Unit])};System.out.println("""shape: [F[_], A](f: F[A])(implicit evidence$3: scalaz.Traverse[F])F[Unit]""");$skip(28); val res$6 = 
    
  shape(List(1, 2, 3));System.out.println("""res6: List[Unit] = """ + $show(res$6));$skip(23); val res$7 = 
  shape(tree).drawTree;System.out.println("""res7: String = """ + $show(res$7));$skip(152); 
  
  
  def decompose[F[_]: Traverse, A](f: F[A]) =
    Applicative[Id].product[({type l[X]=List[A]})#l].traverse(f) { x => (((): Id[Unit]), List(x)) };System.out.println("""decompose: [F[_], A](f: F[A])(implicit evidence$4: scalaz.Traverse[F])(scalaz.Scalaz.Id[F[Unit]], List[A])""");$skip(30); val res$8 = 
  decompose(List(1, 2, 3, 4));System.out.println("""res8: (scalaz.Scalaz.Id[List[Unit]], List[Int]) = """ + $show(res$8));$skip(18); val res$9 = 
  decompose(tree);System.out.println("""res9: (scalaz.Scalaz.Id[scalaz.Tree[Unit]], List[Char]) = """ + $show(res$9));$skip(52); val res$10 = 
  
  
  // Sequence
  List(1.some, 2.some).sequence;System.out.println("""res10: Option[List[Int]] = """ + $show(res$10));$skip(38); val res$11 = 
  List(1.some, 2.some, none).sequence;System.out.println("""res11: Option[List[Int]] = """ + $show(res$11));$skip(133); 
  
  val validationTree: Tree[Validation[String, Int]] = 1.success[String].node(
    2.success[String].leaf, 3.success[String].leaf);System.out.println("""validationTree  : scalaz.Tree[scalaz.Validation[String,Int]] = """ + $show(validationTree ));$skip(75); val res$12 = 
  
  validationTree.sequence[({ type l[X]=Validation[String, X] })#l, Int];System.out.println("""res12: scalaz.Validation[String,scalaz.Tree[Int]] = """ + $show(res$12));$skip(128); 
  val failedTree: Tree[Validation[String, Int]] = 1.success[String].node(
    2.success[String].leaf, "boom".failure[Int].leaf);System.out.println("""failedTree  : scalaz.Tree[scalaz.Validation[String,Int]] = """ + $show(failedTree ));$skip(71); val res$13 = 
  
  failedTree.sequence[({ type l[X]=Validation[String, X] })#l, Int];System.out.println("""res13: scalaz.Validation[String,scalaz.Tree[Int]] = """ + $show(res$13))}
}
