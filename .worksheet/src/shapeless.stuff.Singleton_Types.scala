package shapeless.stuff

import shapeless._

/**
 * Examples from https://github.com/milessabin/shapeless/wiki/Feature-overview:-shapeless-2.0.0
 *
 * They don't seem to work properly in a worksheet but are okay in the repl. This bug fix
 * highlights the issue: https://github.com/scala/scala/pull/3916 (referred from this issue:
 * https://github.com/milessabin/shapeless/issues/197).
 */
object Singleton_Types {
  import syntax.std.tuple._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(484); 
  
  val l = 23 :: "foo" :: true :: HNil;System.out.println("""l  : shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNil]]] = """ + $show(l ));$skip(10); val res$0 = 
  
  l(0);System.out.println("""res0: Int = """ + $show(res$0));$skip(7); val res$1 = 
  l(1);System.out.println("""res1: String = """ + $show(res$1));$skip(31); 
  
  val t = (23, "foo", true);System.out.println("""t  : (Int, String, Boolean) = """ + $show(t ));$skip(7); val res$2 = 
  t(1)
  
  
  import syntax.singleton._;System.out.println("""res2: String = """ + $show(res$2));$skip(49); val res$3 = 
  
  23.narrow;System.out.println("""res3: <error> = """ + $show(res$3));$skip(15); val res$4 = 
  "foo".narrow;System.out.println("""res4: <error> = """ + $show(res$4));$skip(31); 
  
  val bTrue = Witness(true);System.out.println("""bTrue  : shapeless.Witness{type T = Boolean(true)} = """ + $show(bTrue ));$skip(56); 
  val (wTrue, wFalse) = (Witness(true), Witness(false));System.out.println("""wTrue  : shapeless.Witness{type T = Boolean(true)} = """ + $show(wTrue ));System.out.println("""wFalse  : shapeless.Witness{type T = Boolean(false)} = """ + $show(wFalse ));$skip(13); val res$5 = 
  
  
  'foo;System.out.println("""res5: Symbol = """ + $show(res$5));$skip(14); val res$6 = 
  'foo.narrow;System.out.println("""res6: <error> = """ + $show(res$6))}
}
