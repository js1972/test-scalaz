package scalaz.endo.stuff

import play.api.libs.json._
import play.api.libs.functional._
import java.util.{Date, Calendar}


object TestJsonPrettyPrint {

  case class Person(id: Option[Long] = None, firstName: String, lastName: String, birthDate: Date)
  
  def main(args: Array[String]): Unit = {
    
    implicit val personFormat = Json.format[Person]
    
    val now = Calendar.getInstance().getTime() 
    println(Json.prettyPrint(Json.toJson(Person(Some(1), "Arthur", "Dent", now))))
  }

}