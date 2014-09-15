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
  import syntax.std.tuple._
  
  val l = 23 :: "foo" :: true :: HNil             //> l  : shapeless.::[Int,shapeless.::[String,shapeless.::[Boolean,shapeless.HNi
                                                  //| l]]] = 23 :: foo :: true :: HNil
  
  l(0)                                            //> res0: Int = 23
  l(1)                                            //> res1: String = foo
  
  val t = (23, "foo", true)                       //> t  : (Int, String, Boolean) = (23,foo,true)
  t(1)                                            //> res2: String = foo
  
  
  import syntax.singleton._
  
  23.narrow                                       //> res3: <error> = 23
  "foo".narrow                                    //> res4: <error> = foo
  
  val bTrue = Witness(true)                       //> bTrue  : shapeless.Witness{type T = Boolean(true)} = shapeless.stuff.Singlet
                                                  //| on_Types$$anonfun$main$1$fresh$macro$17$1@367cca66
  val (wTrue, wFalse) = (Witness(true), Witness(false))
                                                  //> wTrue  : shapeless.Witness{type T = Boolean(true)} = shapeless.stuff.Singlet
                                                  //| on_Types$$anonfun$main$1$fresh$macro$18$1@2e7c470b
                                                  //| wFalse  : shapeless.Witness{type T = Boolean(false)} = shapeless.stuff.Singl
                                                  //| eton_Types$$anonfun$main$1$fresh$macro$19$1@611c3b04
  
  
  'foo                                            //> res5: Symbol = 'foo
  'foo.narrow                                     //> res6: <error> = 'foo
}