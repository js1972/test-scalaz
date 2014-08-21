package testing

object kind {
  def kind[A: scala.reflect.runtime.universe.TypeTag]: String = {
  import scala.reflect.runtime.universe._
  def typeKind(sig: Type): String = sig match {
    case PolyType(params, resultType) =>
      (params map { p =>
        typeKind(p.typeSignature) match {
          case "*" => "*"
          case s   => "(" + s + ")"
        }
      }).mkString(" -> ") + " -> *"
    case _ => "*"
  }
  def typeSig(tpe: Type): Type = tpe match {
    case SingleType(pre, sym) => sym.companionSymbol.typeSignature
    case ExistentialType(q, TypeRef(pre, sym, args)) => sym.typeSignature
    case TypeRef(pre, sym, args) => sym.typeSignature
  }
  val sig = typeSig(typeOf[A])
  val s = typeKind(sig)
  sig.typeSymbol.name + "'s kind is " + s + ". " + (s match {
    case "*" =>
      "This is a proper type."
    case x if !(x contains "(") =>
      "This is a type constructor: a 1st-order-kinded type."
    case x =>
      "This is a type constructor that takes type constructor(s): a higher-kinded type."
    })
  }                                               //> kind: [A](implicit evidence$2: reflect.runtime.universe.TypeTag[A])String
  
  
  kind[Int]                                       //> res0: String = Int's kind is *. This is a proper type.
  kind[Option.type]                               //> res1: String = Option's kind is * -> *. This is a type constructor: a 1st-o
                                                  //| rder-kinded type.
  kind[Either.type]                               //> res2: String = Either's kind is * -> * -> *. This is a type constructor: a 
                                                  //| 1st-order-kinded type.
  
  import scalaz._
  import Scalaz._
  kind[Show.type]                                 //> res3: String = Show's kind is * -> *. This is a type constructor: a 1st-ord
                                                  //| er-kinded type.
  kind[Equal.type]                                //> res4: String = Equal's kind is * -> *. This is a type constructor: a 1st-or
                                                  //| der-kinded type.
  kind[Functor.type]                              //> res5: String = Functor's kind is (* -> *) -> *. This is a type constructor 
                                                  //| that takes type constructor(s): a higher-kinded type.
}