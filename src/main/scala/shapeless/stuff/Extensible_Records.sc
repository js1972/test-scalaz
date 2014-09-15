package shapeless.stuff

import shapeless._ ; import syntax.singleton._ ; import record._

/**
 * Looks like these are suffering from the same macro issue!
 */
object Extensible_Records {
  val book =
    ("author" ->> "Benjamin Pierce") ::
    ("title"  ->> "Types and Programming Languages") ::
    ("id"     ->>  262162091) ::
    ("price"  ->>  44.11) ::
    HNil                                          //> book  : <error> = Benjamin Pierce :: Types and Programming Languages :: 2621
                                                  //| 62091 :: 44.11 :: HNil
  
  book("author")                                  //> res0: <error> = Benjamin Pierce
  book("title")                                   //> res1: <error> = Types and Programming Languages
  book("id")                                      //> res2: <error> = 262162091
  book("price")                                   //> res3: <error> = 44.11
  
  book.keys                                       //> res4: <error> = author :: title :: id :: price :: HNil
  book.values                                     //> res5: <error> = Benjamin Pierce :: Types and Programming Languages :: 262162
                                                  //| 091 :: 44.11 :: HNil
  
  val newPrice = book("price") + 2.0              //> newPrice  : <error> = 46.11
  val updated = book + ("price" ->> newPrice)     //> updated  : <error> = Benjamin Pierce :: Types and Programming Languages :: 2
                                                  //| 62162091 :: 46.11 :: HNil
  updated("price")                                //> res6: <error> = 46.11
  
  val extended = updated + ("inPrint" ->> true)   //> extended  : <error> = Benjamin Pierce :: Types and Programming Languages :: 
                                                  //| 262162091 :: 46.11 :: true :: HNil
  
  val noId = extended - "id"                      //> noId  : <error> = Benjamin Pierce :: Types and Programming Languages :: 46.1
                                                  //| 1 :: true :: HNil
  //noId("id") Attempting to access a missing field is a compile-time error!
}