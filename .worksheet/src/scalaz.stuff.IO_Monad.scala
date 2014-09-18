package scalaz.stuff

import scalaz._
import Scalaz._
import effect._
import IO._

object IO_Monad {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(169); 
  val action1 = for {
    _ <- putStrLn("Hello World!")
  } yield ();System.out.println("""action1  : scalaz.effect.IO[Unit] = """ + $show(action1 ));$skip(29); 
  
  action1.unsafePerformIO;$skip(42); 
  
  
  val action2 = IO[Unit] {
    
  };System.out.println("""action2  : scalaz.effect.IO[Unit] = """ + $show(action2 ))}
}
