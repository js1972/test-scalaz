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
    };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(595); val res$0 = 
  
  Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte));System.out.println("""res0: scalaz.stuff.lens.Turtle = """ + $show(res$0));$skip(132); 
  
  
  // Using Lens
  val turtlePosition = Lens.lensu[Turtle, Point] (
    (a, value) => a.copy(position = value), _.position
  );System.out.println("""turtlePosition  : scalaz.Lens[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Point] = """ + $show(turtlePosition ));$skip(88); 
  val pointX = Lens.lensu[Point, Double] (
    (a, value) => a.copy(x = value), _.x
  );System.out.println("""pointX  : scalaz.Lens[scalaz.stuff.lens.Point,Double] = """ + $show(pointX ));$skip(191); 
  
  // >=> andThen operator (as with Kelisli)
  // this describes how to get from Turtle.position (which is a Point) to Point.x (which is a Double)
  val turtleX = turtlePosition >=> pointX;System.out.println("""turtleX  : scalaz.LensFamily[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Turtle,Double,Double] = """ + $show(turtleX ));$skip(83); 
  val t0 = Turtle(Point(2.0, 3.0), 0.0, Color(255.toByte, 255.toByte, 255.toByte));System.out.println("""t0  : scalaz.stuff.lens.Turtle = """ + $show(t0 ));$skip(90); val res$1 = 
  
  // provides simple/immutable way to change values within the types
  turtleX.get(t0);System.out.println("""res1: Double = """ + $show(res$1));$skip(23); val res$2 = 
  turtleX.set(t0, 5.0);System.out.println("""res2: scalaz.stuff.lens.Turtle = """ + $show(res$2));$skip(137); val res$3 = 
  
  // using "mod" we can provide a function to operate on a value (get it, apply the function, then set it)
  turtleX.mod(_ + 1.0, t0);System.out.println("""res3: scalaz.stuff.lens.Turtle = """ + $show(res$3));$skip(98); 
  
  // the =>= operator is a variation to mod thats curried
  val incX = turtleX =>= { _ + 1.0 };System.out.println("""incX  : scalaz.stuff.lens.Turtle => scalaz.stuff.lens.Turtle = """ + $show(incX ));$skip(11); val res$4 = 
  incX(t0);System.out.println("""res4: scalaz.stuff.lens.Turtle = """ + $show(res$4));$skip(154); 
  
  // This is starting to look like a State monad.
  //here's another way of writing incX
  val incX2 = for {
    x <- turtleX %= {_ + 1.0}
  } yield x;System.out.println("""incX2  : scalaz.IndexedStateT[scalaz.Id.Id,scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Turtle,Double] = """ + $show(incX2 ));$skip(12); val res$5 = 
  incX2(t0);System.out.println("""res5: scalaz.Id.Id[(scalaz.stuff.lens.Turtle, Double)] = """ + $show(res$5));$skip(216); 
  // %= method takes a function Double => Double and returns a State monad that expresses the change.
  
  
  val turtleHeading = Lens.lensu[Turtle, Double] (
    (a, value) => a.copy(heading = value), _.heading
  );System.out.println("""turtleHeading  : scalaz.Lens[scalaz.stuff.lens.Turtle,Double] = """ + $show(turtleHeading ));$skip(88); 
  val pointY = Lens.lensu[Point, Double] (
    (a, value) => a.copy(y = value), _.y
  );System.out.println("""pointY  : scalaz.Lens[scalaz.stuff.lens.Point,Double] = """ + $show(pointY ));$skip(42); 
  val turtleY = turtlePosition >=> pointY;System.out.println("""turtleY  : scalaz.LensFamily[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Turtle,Double,Double] = """ + $show(turtleY ));$skip(370); 
  
  
  // This is no fun because it feels boilerplatey. But, we can now move turtle forward!
  // Instead of general %=, Scalaz even provides sugars like += for Numeric lenses. Here's what I mean:
  def forward(dist: Double) = for {
    heading <- turtleHeading
    x <- turtleX += dist * math.cos(heading)
    y <- turtleY += dist * math.sin(heading)
  } yield (x, y);System.out.println("""forward: (dist: Double)scalaz.IndexedState[scalaz.stuff.lens.Turtle,scalaz.stuff.lens.Turtle,(Double, Double)]""");$skip(20); val res$6 = 
  forward(10.0)(t0);System.out.println("""res6: scalaz.Id.Id[(scalaz.stuff.lens.Turtle, (Double, Double))] = """ + $show(res$6));$skip(24); val res$7 = 
  forward(10.0) exec t0;System.out.println("""res7: scalaz.Id.Id[scalaz.stuff.lens.Turtle] = """ + $show(res$7))}
}
