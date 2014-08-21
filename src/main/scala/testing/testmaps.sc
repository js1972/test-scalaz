package testing

object testmaps {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  ("a" -> 2)                                      //> res0: (String, Int) = (a,2)
  val map = Map(1 -> "one", 2 -> "two", 3 -> "three", 2 -> "too")
                                                  //> map  : scala.collection.immutable.Map[Int,String] = Map(1 -> one, 2 -> too, 
                                                  //| 3 -> three)
  map.map(_ + "s")                                //> res1: scala.collection.immutable.Iterable[String] = List((1,one)s, (2,too)s,
                                                  //|  (3,three)s)
  
  
  9 % 3                                           //> res2: Int(0) = 0
  
  (1 to 10) map {
    case x if (x % 3 == 0) => println("div by 3")
    case x => println(x)
  }                                               //> 1
                                                  //| 2
                                                  //| div by 3
                                                  //| 4
                                                  //| 5
                                                  //| div by 3
                                                  //| 7
                                                  //| 8
                                                  //| div by 3
                                                  //| 10
                                                  //| res3: scala.collection.immutable.IndexedSeq[Unit] = Vector((), (), (), (), (
                                                  //| ), (), (), (), (), ())
}