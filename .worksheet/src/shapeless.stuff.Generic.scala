package shapeless.stuff

import shapeless._
//import record._, syntax.singleton._

object Generic {
  case class Foo(i: Int, s: String, b: Boolean);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(178); 
  
  val fooGen = Generic[Foo];System.out.println("""fooGen  : <error> = """ + $show(fooGen ));$skip(36); 
  
  val foo = Foo(23, "foo", true);System.out.println("""foo  : shapeless.stuff.Generic.Foo = """ + $show(foo ));$skip(20); val res$0 = 
  
  fooGen.to(foo);System.out.println("""res0: <error> = """ + $show(res$0))}
  
}
