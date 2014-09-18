package scalaz.effect.stuff

import scalaz._
import Scalaz._
import effect._
import IO._
import java.io

object effect_io {

  val action1 = for {
    _ <- putStrLn("Hello, world!")
  } yield ()                                      //> action1  : scalaz.effect.IO[Unit] = scalaz.effect.IOFunctions$$anon$6@13b93c
                                                  //| 4c
  
  action1.unsafePerformIO                         //> Hello, world!
  
  
  // We can also make our own action using the apply method under IO object as follows:
  
  val action2 = IO {
    new java.io.File(".").getAbsolutePath()
  }                                               //> action2  : scalaz.effect.IO[String] = scalaz.effect.IOFunctions$$anon$6@6655
                                                  //| 7791
  
  action2.unsafePerformIO                         //> res0: String = C:\MyScratchFolder\eclipse - scala\.
  
  
  val action3 = IO {
    val source = scala.io.Source.fromFile("eclipse.ini")
    source.getLines.toStream
  }                                               //> action3  : scalaz.effect.IO[scala.collection.immutable.Stream[String]] = sca
                                                  //| laz.effect.IOFunctions$$anon$6@24ebd76a
  
  action3.unsafePerformIO.toList                  //> res1: List[String] = List(-startup, plugins/org.eclipse.equinox.launcher_1.3
                                                  //| .0.v20130327-1440.jar, --launcher.library, plugins/org.eclipse.equinox.launc
                                                  //| her.win32.win32.x86_64_1.1.200.v20140116-2212, -vm, C:/MyScratchFolder/sapjv
                                                  //| m_7/bin/javaw.exe, -vmargs, -Xmx1048m, -Xms100m, -XX:MaxPermSize=256m)
  
  
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