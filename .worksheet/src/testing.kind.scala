package testing

object kind {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(1107); 
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
  };System.out.println("""kind: [A](implicit evidence$2: reflect.runtime.universe.TypeTag[A])String""");$skip(18); val res$0 = 
  
  
  kind[Int];System.out.println("""res0: String = """ + $show(res$0));$skip(20); val res$1 = 
  kind[Option.type];System.out.println("""res1: String = """ + $show(res$1));$skip(20); val res$2 = 
  kind[Either.type]
  
  import scalaz._
  import Scalaz._;System.out.println("""res2: String = """ + $show(res$2));$skip(57); val res$3 = 
  kind[Show.type];System.out.println("""res3: String = """ + $show(res$3));$skip(19); val res$4 = 
  kind[Equal.type];System.out.println("""res4: String = """ + $show(res$4));$skip(21); val res$5 = 
  kind[Functor.type];System.out.println("""res5: String = """ + $show(res$5));$skip(18); val res$6 = 
  kind[List.type];System.out.println("""res6: String = """ + $show(res$6));$skip(25); val res$7 = 
  kind[Applicative.type];System.out.println("""res7: String = """ + $show(res$7));$skip(21); val res$8 = 
  kind[Unapply.type];System.out.println("""res8: String = """ + $show(res$8))}
}
