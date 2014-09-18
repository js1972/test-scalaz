package scalaz.effect.stuff

import scalaz._
import Scalaz._
import effect._
import IO._
import java.io

object effect_io {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(197); 

  val action1 = for {
    _ <- putStrLn("Hello, world!")
  } yield ();System.out.println("""action1  : scalaz.effect.IO[Unit] = """ + $show(action1 ));$skip(30); 
  
  action1.unsafePerformIO;$skip(169); 
  
  
  // We can also make our own action using the apply method under IO object as follows:
  
  val action2 = IO {
    new java.io.File(".").getAbsolutePath()
  };System.out.println("""action2  : scalaz.effect.IO[String] = """ + $show(action2 ));$skip(30); val res$0 = 
  
  action2.unsafePerformIO;System.out.println("""res0: String = """ + $show(res$0));$skip(119); 
  
  
  val action3 = IO {
    val source = scala.io.Source.fromFile("eclipse.ini")
    source.getLines.toStream
  };System.out.println("""action3  : scalaz.effect.IO[scala.collection.immutable.Stream[String]] = """ + $show(action3 ));$skip(37); val res$1 = 
  
  action3.unsafePerformIO.toList;System.out.println("""res1: List[String] = """ + $show(res$1))}
  
  
  // Composing these into programs is done monadically:
  
  // Try this in the REPL as we can't input values at runtime in a worksheet!
  
  //def program: IO[Unit] = for {
  //  line <- readLn
  //  _ <- putStrLn(line)
  //} yield ()
  //
  //program.unsafePerformIO
  
  // IO[Unit] is an instance of Monoid, so we can re-use the monoid addition function |+|.
  //
  //(program |+| program).unsafePerformIO
}
