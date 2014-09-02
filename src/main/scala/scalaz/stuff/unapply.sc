package scalaz.stuff

import scalaz._
import Scalaz._

object unapply {
  def sequenceList[F[_]: Applicative, A](xs: List[F[A]]): F[List[A]] =
    xs.foldRight(List.empty[A].point[F])((a, b) => ^(a, b)(_ :: _))
                                                  //> sequenceList: [F[_], A](xs: List[F[A]])(implicit evidence$2: scalaz.Applicat
                                                  //| ive[F])F[List[A]]
  
  sequenceList(List(some(1), some(2)))            //> res0: Option[List[Int]] = Some(List(1, 2))
  sequenceList(List(some(1),none))                //> res1: Option[List[Int]] = None
  
  
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
  
  sequenceList[({type l[A] = NonEmptyList[String] \/ A})#l, Int](List(\/.right(42), \/.left(NonEmptyList("oops"))))
                                                  //> res2: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = -\/(NonEmptyList(o
                                                  //| ops))
  
  // Unapply, though, does have implicit instances matching the F[A, B] shape, unapplyMAB1 and unapplyMAB2, in its companion so effectively always visible.
  // Lets see if one of them works:
  //Unapply.unapplyMAB1[Applicative, \/, NonEmptyList[String], Int]   <=== Either is right-biased so this one doesn't apply
  val u = Unapply.unapplyMAB2[Applicative, \/, NonEmptyList[String], Int]
                                                  //> u  : scalaz.Unapply[scalaz.Applicative,scalaz.\/[scalaz.NonEmptyList[String
                                                  //| ],Int]]{type M[X] = scalaz.\/[scalaz.NonEmptyList[String],X]; type A = Int}
                                                  //|  = scalaz.Unapply_0$$anon$13@6bba7d61
  
  // This effectively hides the typelambda inside Unapply.
  
  val l = sequenceList[u.M, u.A](List(\/.right(42), \/.left(NonEmptyList("oops"))))
                                                  //> l  : scalaz.stuff.unapply.u.M[List[scalaz.stuff.unapply.u.A]] = -\/(NonEmpt
                                                  //| yList(oops))
  
  // Note that scalaZ provides sequenceU which takes care of the Unaply for us...
  
  // the below shows that scalac can still work out the types outside of sequenceList
  l: NonEmptyList[String] \/ List[Int]            //> res3: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = -\/(NonEmptyList(o
                                                  //| ops))
  
  
  
  // Now that we have worked out Unapply, we can abstract this sequenceList function so that it works for other types and not just Either (\/).
  
  def sequenceListU[FA](xs: List[FA])(implicit U: Unapply[Applicative, FA]): U.M[List[U.A]] =
    sequenceList(U.leibniz.subst(xs))(U.TC)       //> sequenceListU: [FA](xs: List[FA])(implicit U: scalaz.Unapply[scalaz.Applica
                                                  //| tive,FA])U.M[List[U.A]]
  
  sequenceListU(List(\/.right(42), \/.left(NonEmptyList("oops"))))
                                                  //> res4: scalaz.\/[scalaz.NonEmptyList[String],List[Int]] = -\/(NonEmptyList(o
                                                  //| ops))
}