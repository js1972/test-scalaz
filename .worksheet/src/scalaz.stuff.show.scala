package scalaz.stuff

import scalaz._
import Scalaz._


object show {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(78); val res$0 = 
  3.show;System.out.println("""res0: scalaz.Cord = """ + $show(res$0));$skip(10); val res$1 = 
  3.shows;System.out.println("""res1: String = """ + $show(res$1));$skip(18); 
  "hello".println;$skip(13); val res$2 = 
  "hello"(3);System.out.println("""res2: Char = """ + $show(res$2));$skip(24); val res$3 = 
  "hello".show.split(2);System.out.println("""res3: (scalaz.Cord, scalaz.Cord) = """ + $show(res$3));$skip(15); val res$4 = 
  "hello".size;System.out.println("""res4: Int = """ + $show(res$4))}
}
