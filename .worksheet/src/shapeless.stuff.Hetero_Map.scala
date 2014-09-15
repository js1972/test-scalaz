package shapeless.stuff

import shapeless._

object Hetero_Map {
  // Key/value relation to be enforced: Strings map to Ints and vice versa
  class BiMapIS[K, V];import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(215); 
  implicit val intToString = new BiMapIS[Int, String];System.out.println("""intToString  : shapeless.stuff.Hetero_Map.BiMapIS[Int,String] = """ + $show(intToString ));$skip(54); 
  implicit val stringToInt = new BiMapIS[String, Int];System.out.println("""stringToInt  : shapeless.stuff.Hetero_Map.BiMapIS[String,Int] = """ + $show(stringToInt ));$skip(54); 
  
  val hm = HMap[BiMapIS](23 -> "foo", "bar" -> 13);System.out.println("""hm  : shapeless.HMap[shapeless.stuff.Hetero_Map.BiMapIS] = """ + $show(hm ));$skip(88); val res$0 = 
  
  //val hm2 = HMap[BiMapIS](23 -> "foo", 23 -> 13) does not compile!
  
  hm.get(23);System.out.println("""res0: Option[String] = """ + $show(res$0));$skip(16); val res$1 = 
  hm.get("bar")
  
  
  import hm._;System.out.println("""res1: Option[Int] = """ + $show(res$1));$skip(50); 
  val l = 23 :: "bar" :: HNil;System.out.println("""l  : shapeless.::[Int,shapeless.::[String,shapeless.HNil]] = """ + $show(l ));$skip(11); val res$2 = 
  l map hm;System.out.println("""res2: shapeless.::[String,shapeless.::[Int,shapeless.HNil]] = """ + $show(res$2))}
}
