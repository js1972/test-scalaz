package scalaz.stuff

import scalaz._
import Scalaz._


object order {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(80); val res$0 = 
  1 > 2.0;System.out.println("""res0: Boolean(false) = """ + $show(res$0));$skip(17); val res$1 = 
  
  1.0 ?|? 2.0;System.out.println("""res1: scalaz.Ordering = """ + $show(res$1));$skip(17); val res$2 = 
  
  1.0 max 2.0;System.out.println("""res2: Double = """ + $show(res$2))}
}
