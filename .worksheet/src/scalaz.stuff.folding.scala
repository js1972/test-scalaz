package scalaz.stuff

import scalaz._
import Scalaz._
import Tags._


object folding {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(124); val res$0 = 
  List(1, 2, 3).foldRight (1) {_ * _};System.out.println("""res0: Int = """ + $show(res$0));$skip(29); val res$1 = 
  9.some.foldLeft(2) {_ + _};System.out.println("""res1: Int = """ + $show(res$1));$skip(43); val res$2 = 
  
  
  List(1, 2, 3) foldMap { identity };System.out.println("""res2: Int = """ + $show(res$2));$skip(19); val res$3 = 
  Tags.Disjunction;System.out.println("""res3: scalaz.Tag.TagOf[scalaz.Tags.Disjunction] = """ + $show(res$3));$skip(53); val res$4 = 
  Tags.Disjunction(true) |+| Tags.Disjunction(false);System.out.println("""res4: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$4));$skip(59); val res$5 = 
  
  Tags.MinVal.unwrap(Tags.MinVal(1) |+| Tags.MinVal(2));System.out.println("""res5: Int = """ + $show(res$5));$skip(50); val res$6 = 
  Tags.Disjunction.unwrap(Tags.Disjunction(true));System.out.println("""res6: Boolean = """ + $show(res$6));$skip(138); val res$7 = 
  
  //(List(1, 2, 3) foldMap {Tags.Multiplication}: Int) assert_=== 6
  List(true, false, true, true) foldMap { Tags.Disjunction.apply };System.out.println("""res7: scalaz.@@[Boolean,scalaz.Tags.Disjunction] = """ + $show(res$7));$skip(67); 
  
  val l = Tags.Disjunction.subst(List(true, false, true, true));System.out.println("""l  : List[scalaz.@@[Boolean,scalaz.Tags.Disjunction]] = """ + $show(l ))}
  //l foldMap { Tags.Disjunction }
  
  
}
