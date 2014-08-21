package scalaz.stuff

import scalaz._
import Scalaz._


object equal {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(80); val res$0 = 
  1 === 1;System.out.println("""res0: Boolean = """ + $show(res$0));$skip(16); val res$1 = 
  
  1 == "foo";System.out.println("""res1: Boolean = """ + $show(res$1));$skip(127); val res$2 = 
  
  // For not-equals better to use =/= instead of /==
  // as the former has better operator precedence.
  1.some =/= 2.some;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(20); 
  
  1 assert_=== 1
  
  
  
  //Creating our own equal typeclass
  case class TrafficLight(name: String);$skip(118); 
  val red = TrafficLight("red");System.out.println("""red  : scalaz.stuff.equal.TrafficLight = """ + $show(red ));$skip(38); 
  val yellow = TrafficLight("yellow");System.out.println("""yellow  : scalaz.stuff.equal.TrafficLight = """ + $show(yellow ));$skip(36); 
  val green = TrafficLight("green");System.out.println("""green  : scalaz.stuff.equal.TrafficLight = """ + $show(green ));$skip(76); 
  implicit val trafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _);System.out.println("""trafficLightEqual  : scalaz.Equal[scalaz.stuff.equal.TrafficLight] = """ + $show(trafficLightEqual ));$skip(20); val res$3 = 
  
  red === yellow;System.out.println("""res3: Boolean = """ + $show(res$3))}
}
