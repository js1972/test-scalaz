package scalaz.stuff

object cantruthy_test {

  import ToCanIsTruthyOps._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(227); 
  
  //Create a typeclass instance for Int
  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  });System.out.println("""intCanTruthy  : scalaz.stuff.CanTruthy[Int] = """ + $show(intCanTruthy ));$skip(15); val res$0 = 
  
  10.truthy;System.out.println("""res0: Boolean = """ + $show(res$0));$skip(168); 
  
  // Create a typeclass instance for Lists
  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _   => true
  });System.out.println("""listCanTruthy: [A]=> scalaz.stuff.CanTruthy[List[A]]""");$skip(24); val res$1 = 
  
  List("foo").truthy;System.out.println("""res1: Boolean = """ + $show(res$1));$skip(201); 

  
  //Need to create a typeclass instance for Nil because of Nonvariance of type parameter
  implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false);System.out.println("""nilCanTruthy  : scalaz.stuff.CanTruthy[collection.immutable.Nil.type] = """ + $show(nilCanTruthy ));$skip(13); val res$2 = 
  Nil.truthy;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(132); 
  
  
  //Create a typeclass instance for Boolean
  implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity);System.out.println("""booleanCanTruthy  : scalaz.stuff.CanTruthy[Boolean] = """ + $show(booleanCanTruthy ));$skip(15); val res$3 = 
  false.truthy;System.out.println("""res3: Boolean = """ + $show(res$3));$skip(141); 


  //Lets define a truthyIf
  def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
    if (cond.truthy) ifyes
    else ifno;System.out.println("""truthyIf: [A, B, C](cond: A)(ifyes: => B)(ifno: => C)(implicit evidence$1: scalaz.stuff.CanTruthy[A])Any""");$skip(38); val res$4 = 
  
  truthyIf(Nil) {"YEAH!"} {"NO!"};System.out.println("""res4: Any = """ + $show(res$4));$skip(49); val res$5 = 
  truthyIf(2 :: 3 :: 4 :: Nil) {"YEAH!"} {"NO!"};System.out.println("""res5: Any = """ + $show(res$5));$skip(35); val res$6 = 
  truthyIf(true) {"YEAH!"} {"NO!"};System.out.println("""res6: Any = """ + $show(res$6))}
}
