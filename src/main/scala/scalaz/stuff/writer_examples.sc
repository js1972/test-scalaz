package scalaz.stuff

import scalaz._
import std.list._
import syntax.writer._
import syntax.semigroup._ // for |+|
import syntax.apply._     // for |@|
  
object writer_examples {
  type Logger[A] = Writer[List[String], A]

  case class Person(name: String, age: Int, address: Option[Address] = None)
  case class Address(street: String, city: String)
  
  val drWatson =
    for {
      watson <- Person("Watson", 40).set(List("Create Watson"))
      address <- Address("Baker Street", "London").set(List("Create address."))
      _ <- List("tell lets us log something in between. Writer[List[String], Unit]").tell
      moveWatson <- watson.copy(address = Some(address)).set(List("Move to new address."))
    } yield moveWatson                            //> drWatson  : scalaz.WriterT[scalaz.Id.Id,List[String],scalaz.stuff.writer_exa
                                                  //| mples.Person] = WriterT((List(Create Watson, Create address., tell lets us l
                                                  //| og something in between. Writer[List[String], Unit], Move to new address.),P
                                                  //| erson(Watson,40,Some(Address(Baker Street,London)))))
    
  // print log
  drWatson.written.map(println)                   //> Create Watson
                                                  //| Create address.
                                                  //| tell lets us log something in between. Writer[List[String], Unit]
                                                  //| Move to new address.
                                                  //| res0: List[Unit] = List((), (), (), ())
  
  // get value
  drWatson.value.map(println)                     //> Person(Watson,40,Some(Address(Baker Street,London)))
  
  
  val sherlockHolmes =
    for {
      holmes <- Person("Holmes", 40).set(List("Create Holmes"))
      address <- Address("Baker Street", "London").set(List("Create address."))
      moveHolmes <- holmes.copy(address = Some(address)).set(List("Move to new address."))
    } yield moveHolmes                            //> sherlockHolmes  : scalaz.WriterT[scalaz.Id.Id,List[String],scalaz.stuff.wri
                                                  //| ter_examples.Person] = WriterT((List(Create Holmes, Create address., Move t
                                                  //| o new address.),Person(Holmes,40,Some(Address(Baker Street,London)))))
    
  // map lets you map over the value side
  val mapValue: Logger[Option[Address]] = sherlockHolmes.map(x => x.address)
                                                  //> mapValue  : scalaz.stuff.writer_examples.Logger[Option[scalaz.stuff.writer_
                                                  //| examples.Address]] = WriterT((List(Create Holmes, Create address., Move to 
                                                  //| new address.),Some(Address(Baker Street,London))))
  mapValue.value.map(println)                     //> Address(Baker Street,London)
                                                  //| res1: Option[Unit] = Some(())
  
  // with mapWritten you can map over the written side.
  val mapWritten: Logger[Person] = sherlockHolmes.mapWritten(_.map(entry => "[LOG] " + entry))
                                                  //> mapWritten  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_examp
                                                  //| les.Person] = WriterT((List([LOG] Create Holmes, [LOG] Create address., [LO
                                                  //| G] Move to new address.),Person(Holmes,40,Some(Address(Baker Street,London)
                                                  //| ))))
  mapWritten.written.map(println)                 //> [LOG] Create Holmes
                                                  //| [LOG] Create address.
                                                  //| [LOG] Move to new address.
                                                  //| res2: List[Unit] = List((), (), ())
  
  // with mapValue you can map over both sides
  val mValue: Logger[Option[Address]] = sherlockHolmes.mapValue { case (log, p) => (log :+ "Extracting address", p.address) }
                                                  //> mValue  : scalaz.stuff.writer_examples.Logger[Option[scalaz.stuff.writer_ex
                                                  //| amples.Address]] = WriterT((List(Create Holmes, Create address., Move to ne
                                                  //| w address., Extracting address),Some(Address(Baker Street,London))))
  mValue.written.map(println)                     //> Create Holmes
                                                  //| Create address.
                                                  //| Move to new address.
                                                  //| Extracting address
                                                  //| res3: List[Unit] = List((), (), (), ())
  
  // with :++> you can append to the log side of things
  val resultAppend: Logger[Person] = sherlockHolmes :++> List("Finished", "--- new Person ready ---")
                                                  //> resultAppend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_exa
                                                  //| mples.Person] = WriterT((List(Create Holmes, Create address., Move to new a
                                                  //| ddress., Finished, --- new Person ready ---),Person(Holmes,40,Some(Address(
                                                  //| Baker Street,London)))))
  resultAppend.written.map(println)               //> Create Holmes
                                                  //| Create address.
                                                  //| Move to new address.
                                                  //| Finished
                                                  //| --- new Person ready ---
                                                  //| res4: List[Unit] = List((), (), (), (), ())

  // with :++>> you can append using a function
  val resultFappend: Logger[Person] = sherlockHolmes :++>> { x => List("Finished", "--- new Person " + x + " ready ---") }
                                                  //> resultFappend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_ex
                                                  //| amples.Person] = WriterT((List(Create Holmes, Create address., Move to new 
                                                  //| address., Finished, --- new Person Person(Holmes,40,Some(Address(Baker Stre
                                                  //| et,London))) ready ---),Person(Holmes,40,Some(Address(Baker Street,London))
                                                  //| )))
  resultFappend.written.map(println)              //> Create Holmes
                                                  //| Create address.
                                                  //| Move to new address.
                                                  //| Finished
                                                  //| --- new Person Person(Holmes,40,Some(Address(Baker Street,London))) ready -
                                                  //| --
                                                  //| res5: List[Unit] = List((), (), (), (), ())

  // <++: and <<++: work like :++>, :++>> only to prepend information
  val resultPrepend: Logger[Person] = sherlockHolmes.<++:(List("Starting to create a Person"))
                                                  //> resultPrepend  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_ex
                                                  //| amples.Person] = WriterT((List(Starting to create a Person, Create Holmes, 
                                                  //| Create address., Move to new address.),Person(Holmes,40,Some(Address(Baker 
                                                  //| Street,London)))))
  resultPrepend.written.map(println)              //> Starting to create a Person
                                                  //| Create Holmes
                                                  //| Create address.
                                                  //| Move to new address.
                                                  //| res6: List[Unit] = List((), (), (), ())

  // reset your log to zero
  val logNoGood: Logger[Person] = sherlockHolmes.reset
                                                  //> logNoGood  : scalaz.stuff.writer_examples.Logger[scalaz.stuff.writer_exampl
                                                  //| es.Person] = WriterT((List(),Person(Holmes,40,Some(Address(Baker Street,Lon
                                                  //| don)))))
  logNoGood.written.map(println)                  //> res7: List[Unit] = List()
  
  
  // Writer is an applicative, you can easily combine different results.
  val combined: Logger[List[Person]] = (sherlockHolmes |@| drWatson) { List(_) |+| List(_) }
                                                  //> combined  : scalaz.stuff.writer_examples.Logger[List[scalaz.stuff.writer_ex
                                                  //| amples.Person]] = WriterT((List(Create Holmes, Create address., Move to new
                                                  //|  address., Create Watson, Create address., tell lets us log something in be
                                                  //| tween. Writer[List[String], Unit], Move to new address.),List(Person(Holmes
                                                  //| ,40,Some(Address(Baker Street,London))), Person(Watson,40,Some(Address(Bake
                                                  //| r Street,London))))))
  combined.written.map(println)                   //> Create Holmes
                                                  //| Create address.
                                                  //| Move to new address.
                                                  //| Create Watson
                                                  //| Create address.
                                                  //| tell lets us log something in between. Writer[List[String], Unit]
                                                  //| Move to new address.
                                                  //| res8: List[Unit] = List((), (), (), (), (), (), ())
}