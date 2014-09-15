package shapeless.stuff

import shapeless._ ; import syntax.singleton._ ; import record._

/**
 * Looks like these are suffering from the same macro issue!
 */
object Extensible_Records {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(367); 
  val book =
    ("author" ->> "Benjamin Pierce") ::
    ("title"  ->> "Types and Programming Languages") ::
    ("id"     ->>  262162091) ::
    ("price"  ->>  44.11) ::
    HNil;System.out.println("""book  : <error> = """ + $show(book ));$skip(20); val res$0 = 
  
  book("author");System.out.println("""res0: <error> = """ + $show(res$0));$skip(16); val res$1 = 
  book("title");System.out.println("""res1: <error> = """ + $show(res$1));$skip(13); val res$2 = 
  book("id");System.out.println("""res2: <error> = """ + $show(res$2));$skip(16); val res$3 = 
  book("price");System.out.println("""res3: <error> = """ + $show(res$3));$skip(15); val res$4 = 
  
  book.keys;System.out.println("""res4: <error> = """ + $show(res$4));$skip(14); val res$5 = 
  book.values;System.out.println("""res5: <error> = """ + $show(res$5));$skip(40); 
  
  val newPrice = book("price") + 2.0;System.out.println("""newPrice  : <error> = """ + $show(newPrice ));$skip(46); 
  val updated = book + ("price" ->> newPrice);System.out.println("""updated  : <error> = """ + $show(updated ));$skip(19); val res$6 = 
  updated("price");System.out.println("""res6: <error> = """ + $show(res$6));$skip(51); 
  
  val extended = updated + ("inPrint" ->> true);System.out.println("""extended  : <error> = """ + $show(extended ));$skip(32); 
  
  val noId = extended - "id";System.out.println("""noId  : <error> = """ + $show(noId ))}
  //noId("id") Attempting to access a missing field is a compile-time error!
}
