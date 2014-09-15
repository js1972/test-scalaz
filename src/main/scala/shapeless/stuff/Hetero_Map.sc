package shapeless.stuff

import shapeless._

object Hetero_Map {
  // Key/value relation to be enforced: Strings map to Ints and vice versa
  class BiMapIS[K, V]
  implicit val intToString = new BiMapIS[Int, String]
                                                  //> intToString  : shapeless.stuff.Hetero_Map.BiMapIS[Int,String] = shapeless.st
                                                  //| uff.Hetero_Map$BiMapIS@53424146
  implicit val stringToInt = new BiMapIS[String, Int]
                                                  //> stringToInt  : shapeless.stuff.Hetero_Map.BiMapIS[String,Int] = shapeless.st
                                                  //| uff.Hetero_Map$BiMapIS@2f7af3
  
  val hm = HMap[BiMapIS](23 -> "foo", "bar" -> 13)//> hm  : shapeless.HMap[shapeless.stuff.Hetero_Map.BiMapIS] = shapeless.HMap@32
                                                  //| 029f7c
  
  //val hm2 = HMap[BiMapIS](23 -> "foo", 23 -> 13) does not compile!
  
  hm.get(23)                                      //> res0: Option[String] = Some(foo)
  hm.get("bar")                                   //> res1: Option[Int] = Some(13)
  
  
  import hm._
  val l = 23 :: "bar" :: HNil                     //> l  : shapeless.::[Int,shapeless.::[String,shapeless.HNil]] = 23 :: bar :: HN
                                                  //| il
  l map hm                                        //> res2: shapeless.::[String,shapeless.::[Int,shapeless.HNil]] = foo :: 13 :: H
                                                  //| Nil
}