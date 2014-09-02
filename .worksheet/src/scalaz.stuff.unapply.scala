package scalaz.stuff

import scalaz._
import Scalaz._

object unapply {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(210); 
  def sequenceList[F[_]: Applicative, A](xs: List[F[A]]): F[List[A]] =
    xs.foldRight(List.empty[A].point[F])((a, b) => ^(a, b)(_ :: _));System.out.println("""sequenceList: [F[_], A](xs: List[F[A]])(implicit evidence$2: scalaz.Applicative[F])F[List[A]]""");$skip(42); val res$0 = 
  
  sequenceList(List(some(1), some(2)));System.out.println("""res0: Option[List[Int]] = """ + $show(res$0));$skip(35); val res$1 = 
  sequenceList(List(some(1),none));System.out.println("""res1: Option[List[Int]] = """ + $show(res$1));$skip(1446); val res$2 = 
  
  
  // Scalac is not able to infer the types below (commented-out) - it wants an F[A], but Either is F[A, B],
  // but we can get past it with an ugly typelambda.
  
  //sequenceList(List(\/.right(42), \/.left(NonEmptyList("oops"))))
  // type mismatch; found : List[scalaz.\/[scalaz.NonEmptyList[String],Int]] required:
  // List[F[A]] - type mismatch; found : List[scalaz.\/[scalaz.NonEmptyList[String],Int]] required: List[F[A]] - no type
  // parameters for method sequenceList: (xs: List[F[A]])(implicit evidence$1: scalaz.Applicative[F])F[List[A]] exist so
  // that it can be applied to arguments (List[scalaz.\/[scalaz.NonEmptyList[String],Int]]) --- because --- argument
  // expression's type is not compatible with formal parameter type; found : List[scalaz.\/
  // [scalaz.NonEmptyList[String],Int]] required: List[?F[?A]] - could not find implicit value for evidence parameter of
  // type scalaz.Applicative[F] - no type parameters for method sequenceList: (xs: List[F[A]])(implicit evidence$1:
  // scalaz.Applicative[F])F[List[A]] exist so that it can be applied to arguments (List[scalaz.\/
  // [scalaz.NonEmptyList[String],Int]]) --- because --- argument expression's type is not compatible with formal
  // parameter type; found : List[scalaz.\/[scalaz.NonEmptyList[String],Int]] required: List[?F[?A]]
  
  sequenceList[({type l[A] = NonEmptyList[String] \/ A})#l, Int](List(\/.right(42), \/.left(NonEmptyList("oops"))));System.out.println("""res2: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = """ + $show(res$2));$skip(393); 
  
  // Unapply, though, does have implicit instances matching the F[A, B] shape, unapplyMAB1 and unapplyMAB2, in its companion so effectively always visible.
  // Lets see if one of them works:
  //Unapply.unapplyMAB1[Applicative, \/, NonEmptyList[String], Int]   <=== Either is right-biased so this one doesn't apply
  val u = Unapply.unapplyMAB2[Applicative, \/, NonEmptyList[String], Int];System.out.println("""u  : scalaz.Unapply[scalaz.Applicative,scalaz.\/[scalaz.NonEmptyList[String],Int]]{type M[X] = scalaz.\/[scalaz.NonEmptyList[String],X]; type A = Int} = """ + $show(u ));$skip(149); 
  
  // This effectively hides the typelambda inside Unapply.
  
  val l = sequenceList[u.M, u.A](List(\/.right(42), \/.left(NonEmptyList("oops"))));System.out.println("""l  : scalaz.stuff.unapply.u.M[List[scalaz.stuff.unapply.u.A]] = """ + $show(l ));$skip(213); val res$3 = 
  
  // Note that scalaZ provides sequenceU which takes care of the Unaply for us...
  
  // the below shows that scalac can still work out the types outside of sequenceList
  l: NonEmptyList[String] \/ List[Int];System.out.println("""res3: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = """ + $show(res$3));$skip(294); 
  
  
  
  // Now that we have worked out Unapply, we can abstract this sequenceList function so that it works for other types and not just Either (\/).
  
  def sequenceListU[FA](xs: List[FA])(implicit U: Unapply[Applicative, FA]): U.M[List[U.A]] =
    sequenceList(U.leibniz.subst(xs))(U.TC);System.out.println("""sequenceListU: [FA](xs: List[FA])(implicit U: scalaz.Unapply[scalaz.Applicative,FA])U.M[List[U.A]]""");$skip(70); val res$4 = 
  
  sequenceListU(List(\/.right(42), \/.left(NonEmptyList("oops"))));System.out.println("""res4: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = """ + $show(res$4))}
}
