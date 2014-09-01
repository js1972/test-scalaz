package scalaz.stuff

import scalaz._
import Scalaz._

object lens {
  case class Point(x: Double, y: Double)
  case class Color(r: Byte, g: Byte, b: Byte)
  
  case class Turtle(position: Point, heading: Double, color: Color) {
    
    //  moving the turtle without breaking immutability - nested copies - ugly!
    def forward(dist: Double): Turtle =
      copy(position =
        position.copy(
          x = position.x + dist * math.cos(heading),
          y = position.y + dist * math.sin(heading)
      ))
    }
  
  Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte))
                                                  //> res0: scalaz.stuff.lens.Turtle = Turtle(Point(2.0,3.0),0.0,Color(-1,-1,-1))
                                                  //| 
  
  
  // Using Lens
  val turtlePosition = Lens.lensu[Turtle, Point] (
    (a, value) => a.copy(position = value), _.position
  )                                               //> turtlePosition  : scalaz.Lens[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Poi
                                                  //| nt] = scalaz.LensFunctions$$anon$5@4ce10d40
  val pointX = Lens.lensu[Point, Double] (
    (a, value) => a.copy(x = value), _.x
  )                                               //> pointX  : scalaz.Lens[scalaz.stuff.lens.Point,Double] = scalaz.LensFunctions
                                                  //| $$anon$5@384e7c49
  
  // >=> andThen operator (as with Kelisli)
  // this describes how to get from Turtle.position (which is a Point) to Point.x (which is a Double)
  val turtleX = turtlePosition >=> pointX         //> turtleX  : scalaz.LensFamily[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Tur
                                                  //| tle,Double,Double] = scalaz.LensFamilyFunctions$$anon$4@5ef80a07
  val t0 = Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte))
                                                  //> t0  : scalaz.stuff.lens.Turtle = Turtle(Point(2.0,3.0),0.0,Color(-1,-1,-1))
                                                  //| 
  
  // provides simple/immutable way to change values within the types
  turtleX.get(t0)                                 //> res1: Double = 2.0
  turtleX.set(t0, 5.0)                            //> res2: scalaz.stuff.lens.Turtle = Turtle(Point(5.0,3.0),0.0,Color(-1,-1,-1))
                                                  //| 
  
  // using "mod" we can provide a function to operate on a value (get it, apply the function, then set it)
  turtleX.mod(_ + 1.0, t0)                        //> res3: scalaz.stuff.lens.Turtle = Turtle(Point(3.0,3.0),0.0,Color(-1,-1,-1))
                                                  //| 
  
  // the =>= operator is a variation to mod thats curried
  val incX = turtleX =>= { _ + 1.0 }              //> incX  : scalaz.stuff.lens.Turtle => scalaz.stuff.lens.Turtle = <function1>
                                                  //| 
  incX(t0)                                        //> res4: scalaz.stuff.lens.Turtle = Turtle(Point(3.0,3.0),0.0,Color(-1,-1,-1))
                                                  //| 
  
  // This is starting to look like a State monad.
  //here's another way of writing incX
  val incX2 = for {
    x <- turtleX %= {_ + 1.0}
  } yield x                                       //> incX2  : scalaz.IndexedStateT[scalaz.Id.Id,scalaz.stuff.lens.Turtle,scalaz.
                                                  //| stuff.lens.Turtle,Double] = scalaz.IndexedStateT$$anon$10@56d0c5bb
  incX2(t0)                                       //> res5: scalaz.Id.Id[(scalaz.stuff.lens.Turtle, Double)] = (Turtle(Point(3.0,
                                                  //| 3.0),0.0,Color(-1,-1,-1)),3.0)
  // %= method takes a function Double => Double and returns a State monad that expresses the change.
  
  
  val turtleHeading = Lens.lensu[Turtle, Double] (
    (a, value) => a.copy(heading = value), _.heading
  )                                               //> turtleHeading  : scalaz.Lens[scalaz.stuff.lens.Turtle,Double] = scalaz.Lens
                                                  //| Functions$$anon$5@374f2c8b
  val pointY = Lens.lensu[Point, Double] (
    (a, value) => a.copy(y = value), _.y
  )                                               //> pointY  : scalaz.Lens[scalaz.stuff.lens.Point,Double] = scalaz.LensFunction
                                                  //| s$$anon$5@59023ece
  val turtleY = turtlePosition >=> pointY         //> turtleY  : scalaz.LensFamily[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Tur
                                                  //| tle,Double,Double] = scalaz.LensFamilyFunctions$$anon$4@22697409
  
  
  // This is no fun because it feels boilerplatey. But, we can now move turtle forward!
  // Instead of general %=, Scalaz even provides sugars like += for Numeric lenses. Here's what I mean:
  def forward(dist: Double) = for {
    heading <- turtleHeading
    x <- turtleX += dist * math.cos(heading)
    y <- turtleY += dist * math.sin(heading)
  } yield (x, y)                                  //> forward: (dist: Double)scalaz.IndexedState[scalaz.stuff.lens.Turtle,scalaz.
                                                  //| stuff.lens.Turtle,(Double, Double)]
  forward(10.0)(t0)                               //> res6: scalaz.Id.Id[(scalaz.stuff.lens.Turtle, (Double, Double))] = (Turtle(
                                                  //| Point(12.0,3.0),0.0,Color(-1,-1,-1)),(12.0,3.0))
  forward(10.0) exec t0                           //> res7: scalaz.Id.Id[scalaz.stuff.lens.Turtle] = Turtle(Point(12.0,3.0),0.0,C
                                                  //| olor(-1,-1,-1))
}