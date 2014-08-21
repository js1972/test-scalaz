package testing

object testmaps {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(78); 
  println("Welcome to the Scala worksheet");$skip(16); val res$0 = 
  
  ("a" -> 2);System.out.println("""res0: (String, Int) = """ + $show(res$0));$skip(66); 
  val map = Map(1 -> "one", 2 -> "two", 3 -> "three", 2 -> "too");System.out.println("""map  : scala.collection.immutable.Map[Int,String] = """ + $show(map ));$skip(19); val res$1 = 
  map.map(_ + "s");System.out.println("""res1: scala.collection.immutable.Iterable[String] = """ + $show(res$1));$skip(14); val res$2 = 
  
  
  9 % 3;System.out.println("""res2: Int(0) = """ + $show(res$2));$skip(101); val res$3 = 
  
  (1 to 10) map {
    case x if (x % 3 == 0) => println("div by 3")
    case x => println(x)
  };System.out.println("""res3: scala.collection.immutable.IndexedSeq[Unit] = """ + $show(res$3))}
}
