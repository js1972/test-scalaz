package scalaz.stuff

import scalaz._
import Scalaz._

import Kleisli._

import scala.util._


object kleisli_testing {
  // A Kleisli is a wrapper for a function of type A => M[B]
  // >=> is an alias for andThen
  // <=< is an alias for compose
 
  val f = Kleisli { (x: Int) => (x + 1).some }    //> f  : scalaz.Kleisli[Option,Int,Int] = Kleisli(<function1>)
  val g = Kleisli { (x: Int) => (x * 100).some }  //> g  : scalaz.Kleisli[Option,Int,Int] = Kleisli(<function1>)
  
  // 4.some flatMap (f compose g) - rhs first
  4.some >>= (f <=< g)                            //> res0: Option[Int] = Some(401)
  // lhs first
  4.some >>= (f >=> g)                            //> res1: Option[Int] = Some(500)
  
  
  val addStuff: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b                                   //> addStuff  : scalaz.Reader[Int,Int] = Kleisli(<function1>)
  
  addStuff(3)                                     //> res2: scalaz.Id.Id[Int] = 19
  
  
  // === SORE EXAMPLES FROM THE SCALAZ EXAMPLES ON GITHUB ===

  // just some trivial data structures:
  // Continents contain countries. Countries contain cities.
  case class Continent(name: String, countries: List[Country] = List.empty)
  case class Country(name: String, cities: List[City] = List.empty)
  case class City(name: String, isCapital: Boolean = false, inhabitants: Int = 20)

  val data: List[Continent] = List(
    Continent("Europe"),
    Continent("America",
      List(
        Country("USA",
          List(
            City("Washington"), City("New York"))))),
    Continent("Asia",
      List(
        Country("India",
          List(City("New Dehli"), City("Calcutta"))))))
                                                  //> data  : List[scalaz.stuff.kleisli_testing.Continent] = List(Continent(Europ
                                                  //| e,List()), Continent(America,List(Country(USA,List(City(Washington,false,20
                                                  //| ), City(New York,false,20))))), Continent(Asia,List(Country(India,List(City
                                                  //| (New Dehli,false,20), City(Calcutta,false,20))))))

  def continents(name: String): List[Continent] =
    data.filter(k => k.name.contains(name))       //> continents: (name: String)List[scalaz.stuff.kleisli_testing.Continent]

  def countries(continent: Continent): List[Country] = continent.countries
                                                  //> countries: (continent: scalaz.stuff.kleisli_testing.Continent)List[scalaz.s
                                                  //| tuff.kleisli_testing.Country]

  def cities(country: Country): List[City] = country.cities
                                                  //> cities: (country: scalaz.stuff.kleisli_testing.Country)List[scalaz.stuff.kl
                                                  //| eisli_testing.City]

  def save(cities: List[City]): Try[Unit] =
    Try {
      cities.foreach(c => println("Saving " + c.name))
    }                                             //> save: (cities: List[scalaz.stuff.kleisli_testing.City])scala.util.Try[Unit]
                                                  //| 

  def inhabitants(c: City): Int = c.inhabitants   //> inhabitants: (c: scalaz.stuff.kleisli_testing.City)Int
  
  // allCities and allCities are examples of using the variations of the
  // andThen operator, either starting with a kleisli arrow and following with
  // functions of the form A => M[B] or following with adequate kleisli arrows.
  // The aliases are: >==> and andThenK
  //                  >=>  and andThen
  // the same applies to function composition with
  // <==<, <=<, composeK and compose
  val allCities = kleisli(continents) >==> countries >==> cities
                                                  //> allCities  : scalaz.Kleisli[List,String,scalaz.stuff.kleisli_testing.City] 
                                                  //| = Kleisli(<function1>)
  val allCities2 = kleisli(continents) >=> kleisli(countries) >=> kleisli(cities)
                                                  //> allCities2  : scalaz.Kleisli[List,String,scalaz.stuff.kleisli_testing.City]
                                                  //|  = Kleisli(<function1>)

  (allCities("America")).map(println)             //> City(Washington,false,20)
                                                  //| City(New York,false,20)
                                                  //| res3: List[Unit] = List((), ())

  // =<< takes a monadical structure compatible with the kleislifunction
  // as its parameter and flatmaps the function over this parameter.
  (allCities =<< List("Amer", "Asi")).map(println)//> City(Washington,false,20)
                                                  //| City(New York,false,20)
                                                  //| City(New Dehli,false,20)
                                                  //| City(Calcutta,false,20)
                                                  //| res4: List[Unit] = List((), (), (), ())

  // with map we can map a function B => C over a kleisli function of the
  // structure A => M[B]
  val cityInhabitants = allCities map inhabitants //> cityInhabitants  : scalaz.Kleisli[List,String,Int] = Kleisli(<function1>)
  (cityInhabitants) =<< List("Amer", "Asi")       //> res5: List[Int] = List(20, 20, 20, 20)

  // with mapK you can map a kleisli function into
  // another monadic structure, e.g. provide a function
  // M[A] => N[B]
  // Note : the example is not particularily useful here.
  val getandSave = (allCities mapK save)          //> getandSave  : scalaz.Kleisli[scala.util.Try,String,Unit] = Kleisli(<functio
                                                  //| n1>)
  getandSave("America").map(println)              //> Saving Washington
                                                  //| Saving New York
                                                  //| ()
                                                  //| res6: scala.util.Try[Unit] = Success(())

  // local can be used to prepend a kleisli function of
  // the form A => M[B] with a function of the form
  // AA => A, resulting in a kleisli function of the form
  // AA => M[B]
  def index(i: Int) = data(i).name                //> index: (i: Int)String
  val allCitiesByIndex = allCities local index    //> allCitiesByIndex  : scalaz.Kleisli[List,Int,scalaz.stuff.kleisli_testing.Ci
                                                  //| ty] = Kleisli(<function1>)
  allCitiesByIndex(1).map(println)                //> City(Washington,false,20)
                                                  //| City(New York,false,20)
                                                  //| res7: List[Unit] = List((), ())
}