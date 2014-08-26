package testing

import play.api.libs.json._
import play.api.libs.functional._
import java.util.{Date, Calendar}


/**
 * How to use play-json to prettyprint case scala structures
 */
object TestJsonPrettyPrint {

  case class Stuff(first: String, second: String)
  case class Person(id: Option[Long] = None, firstName: String, lastName: String, birthDate: Option[Date] = None, bd: BigDecimal, stuff: Stuff)
  
  def main(args: Array[String]): Unit = {
    
    // Need to add an implicit val for each case class as well as embedded case classes
    implicit val stuffFormat = Json.format[Stuff]
    implicit val personFormat = Json.format[Person]
    
    val now = Calendar.getInstance().getTime() 
    println(Json.prettyPrint(Json.toJson(Person(Some(1), "Arthur", "Dent", Some(now), 100,  Stuff("first", "second")))))
  }

}