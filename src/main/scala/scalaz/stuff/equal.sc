package scalaz.stuff

import scalaz._
import Scalaz._


object equal {
  1 === 1                                         //> res0: Boolean = true
  
  1 == "foo"                                      //> res1: Boolean = false
  
  // For not-equals better to use =/= instead of /==
  // as the former has better operator precedence.
  1.some =/= 2.some                               //> res2: Boolean = true
  
  1 assert_=== 1
  
  
  
  //Creating our own equal typeclass
  case class TrafficLight(name: String)
  val red = TrafficLight("red")                   //> red  : scalaz.stuff.equal.TrafficLight = TrafficLight(red)
  val yellow = TrafficLight("yellow")             //> yellow  : scalaz.stuff.equal.TrafficLight = TrafficLight(yellow)
  val green = TrafficLight("green")               //> green  : scalaz.stuff.equal.TrafficLight = TrafficLight(green)
  implicit val trafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)
                                                  //> trafficLightEqual  : scalaz.Equal[scalaz.stuff.equal.TrafficLight] = scalaz.
                                                  //| Equal$$anon$7@192ba2d4
  
  red === yellow                                  //> res3: Boolean = false
}