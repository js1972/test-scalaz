package scalaz.stuff

import scalaz._
import std.list._
import syntax.writer._
import syntax.semigroup._ // for |+|
import syntax.apply._     // for |@|
  
object writer_examples {
  type Logger[A] = Writer[List[String], A]

  case class Person(name: String, age: Int, address: Option[Address] = None)
  case class Address(street: String, city: String);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(730); 
  
  val drWatson =
    for {
      watson <- Person("Watson", 40).set(List("Create Watson"))
      address <- Address("Baker Street", "London").set(List("Create address."))
      _ <- List("tell lets us log something in between. Writer[List[String], Unit]").tell
      moveWatson <- watson.copy(address = Some(address)).set(List("Move to new address."))
    } yield moveWatson;System.out.println("""drWatson  : scalaz.WriterT[scalaz.Id.Id,List[String],scalaz.stuff.writer_examples.Person] = """ + $show(drWatson ));$skip(52); val res$0 = 
    
  // print log
  drWatson.written.map(println);System.out.println("""res0: List[Unit] = """ + $show(res$0));$skip(48); 
  
  // get value
  drWatson.value.map(println);$skip(297); 
  
  
  val sherlockHolmes =
    for {
      holmes <- Person("Holmes", 40).set(List("Create Holmes"))
      address <- Address("Baker Street", "London").set(List("Create address."))
      moveHolmes <- holmes.copy(address = Some(address)).set(List("Move to new address."))
    } yield moveHolmes;System.out.println("""sherlockHolmes  : scalaz.WriterT[scalaz.Id.Id,List[String],scalaz.stuff.writer_examples.Person] = """ + $show(sherlockHolmes ));$skip(124); 
    
  // map lets you map over the value side
  val mapValue: Logger[Option[Address]] = sherlockHolmes.map(x => x.address);System.out.println("""mapValue  : scalaz.stuff.writer_examples.Logger[Option[scalaz.stuff.writer_examples.Address]] = """ + $show(mapValue ));$skip(30); val res$1 = 
  mapValue.value.map(println);System.out.println("""res1: Option[Unit] = """ + $show(res$1));$skip(154); 
  
  // with mapWritten you can map over the written side.
  val mapWritten: Logger[Person] = sherlockHolmes.mapWritten(_.map(entry => "[LOG] " + entry));System.out.println("""mapWritten  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examples.Person] = """ + $show(mapWritten ));$skip(34); val res$2 = 
  mapWritten.written.map(println);System.out.println("""res2: List[Unit] = """ + $show(res$2));$skip(176); 
  
  // with mapValue you can map over both sides
  val mValue: Logger[Option[Address]] = sherlockHolmes.mapValue { case (log, p) => (log :+ "Extracting address", p.address) };System.out.println("""mValue  : scalaz.stuff.writer_examples.Logger[Option[scalaz.stuff.writer_examples.Address]] = """ + $show(mValue ));$skip(30); val res$3 = 
  mValue.written.map(println);System.out.println("""res3: List[Unit] = """ + $show(res$3));$skip(161); 
  
  // with :++> you can append to the log side of things
  val resultAppend: Logger[Person] = sherlockHolmes :++> List("Finished", "--- new Person ready ---");System.out.println("""resultAppend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examples.Person] = """ + $show(resultAppend ));$skip(36); val res$4 = 
  resultAppend.written.map(println);System.out.println("""res4: List[Unit] = """ + $show(res$4));$skip(172); 

  // with :++>> you can append using a function
  val resultFappend: Logger[Person] = sherlockHolmes :++>> { x => List("Finished", "--- new Person " + x + " ready ---") };System.out.println("""resultFappend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examples.Person] = """ + $show(resultFappend ));$skip(37); val res$5 = 
  resultFappend.written.map(println);System.out.println("""res5: List[Unit] = """ + $show(res$5));$skip(166); 

  // <++: and <<++: work like :++>, :++>> only to prepend information
  val resultPrepend: Logger[Person] = sherlockHolmes.<++:(List("Starting to create a Person"));System.out.println("""resultPrepend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examples.Person] = """ + $show(resultPrepend ));$skip(37); val res$6 = 
  resultPrepend.written.map(println);System.out.println("""res6: List[Unit] = """ + $show(res$6));$skip(84); 

  // reset your log to zero
  val logNoGood: Logger[Person] = sherlockHolmes.reset;System.out.println("""logNoGood  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examples.Person] = """ + $show(logNoGood ));$skip(33); val res$7 = 
  logNoGood.written.map(println);System.out.println("""res7: List[Unit] = """ + $show(res$7));$skip(172); 
  
  
  // Writer is an applicative, you can easily combine different results.
  val combined: Logger[List[Person]] = (sherlockHolmes |@| drWatson) { List(_) |+| List(_) };System.out.println("""combined  : scalaz.stuff.writer_examples.Logger[List[scalaz.stuff.writer_examples.Person]] = """ + $show(combined ));$skip(32); val res$8 = 
  combined.written.map(println);System.out.println("""res8: List[Unit] = """ + $show(res$8))}
}
