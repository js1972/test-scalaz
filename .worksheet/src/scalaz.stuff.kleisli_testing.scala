package scalaz.stuff

import scalaz._
import Scalaz._

import Kleisli._

import scala.util._


object kleisli_testing {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(301); 
  // A Kleisli is a wrapper for a function of type A => M[B]
  // >=> is an alias for andThen
  // <=< is an alias for compose
 
  val f = Kleisli { (x: Int) => (x + 1).some };System.out.println("""f  : scalaz.Kleisli[Option,Int,Int] = """ + $show(f ));$skip(49); 
  val g = Kleisli { (x: Int) => (x * 100).some };System.out.println("""g  : scalaz.Kleisli[Option,Int,Int] = """ + $show(g ));$skip(73); val res$0 = 
  
  // 4.some flatMap (f compose g) - rhs first
  4.some >>= (f <=< g);System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(38); val res$1 = 
  // lhs first
  4.some >>= (f >=> g);System.out.println("""res1: Option[Int] = """ + $show(res$1));$skip(132); 
  
  
  val addStuff: Reader[Int, Int] = for {
    a <- Reader { (_: Int) * 2 }
    b <- Reader { (_: Int) + 10 }
  } yield a + b;System.out.println("""addStuff  : scalaz.Reader[Int,Int] = """ + $show(addStuff ));$skip(18); val res$2 = 
  
  addStuff(3)
  
  
  // === SORE EXAMPLES FROM THE SCALAZ EXAMPLES ON GITHUB ===

  // just some trivial data structures:
  // Continents contain countries. Countries contain cities.
  case class Continent(name: String, countries: List[Country] = List.empty)
  case class Country(name: String, cities: List[City] = List.empty)
  case class City(name: String, isCapital: Boolean = false, inhabitants: Int = 20);System.out.println("""res2: scalaz.Id.Id[Int] = """ + $show(res$2));$skip(708); 

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
          List(City("New Dehli"), City("Calcutta"))))));System.out.println("""data  : List[scalaz.stuff.kleisli_testing.Continent] = """ + $show(data ));$skip(96); 

  def continents(name: String): List[Continent] =
    data.filter(k => k.name.contains(name));System.out.println("""continents: (name: String)List[scalaz.stuff.kleisli_testing.Continent]""");$skip(77); 

  def countries(continent: Continent): List[Country] = continent.countries;System.out.println("""countries: (continent: scalaz.stuff.kleisli_testing.Continent)List[scalaz.stuff.kleisli_testing.Country]""");$skip(62); 

  def cities(country: Country): List[City] = country.cities;System.out.println("""cities: (country: scalaz.stuff.kleisli_testing.Country)List[scalaz.stuff.kleisli_testing.City]""");$skip(117); 

  def save(cities: List[City]): Try[Unit] =
    Try {
      cities.foreach(c => println("Saving " + c.name))
    };System.out.println("""save: (cities: List[scalaz.stuff.kleisli_testing.City])scala.util.Try[Unit]""");$skip(50); 

  def inhabitants(c: City): Int = c.inhabitants;System.out.println("""inhabitants: (c: scalaz.stuff.kleisli_testing.City)Int""");$skip(467); 
  
  // allCities and allCities are examples of using the variations of the
  // andThen operator, either starting with a kleisli arrow and following with
  // functions of the form A => M[B] or following with adequate kleisli arrows.
  // The aliases are: >==> and andThenK
  //                  >=>  and andThen
  // the same applies to function composition with
  // <==<, <=<, composeK and compose
  val allCities = kleisli(continents) >==> countries >==> cities;System.out.println("""allCities  : scalaz.Kleisli[List,String,scalaz.stuff.kleisli_testing.City] = """ + $show(allCities ));$skip(82); 
  val allCities2 = kleisli(continents) >=> kleisli(countries) >=> kleisli(cities);System.out.println("""allCities2  : scalaz.Kleisli[List,String,scalaz.stuff.kleisli_testing.City] = """ + $show(allCities2 ));$skip(40); val res$3 = 

  (allCities("America")).map(println);System.out.println("""res3: List[Unit] = """ + $show(res$3));$skip(195); val res$4 = 

  // =<< takes a monadical structure compatible with the kleislifunction
  // as its parameter and flatmaps the function over this parameter.
  (allCities =<< List("Amer", "Asi")).map(println);System.out.println("""res4: List[Unit] = """ + $show(res$4));$skip(151); 

  // with map we can map a function B => C over a kleisli function of the
  // structure A => M[B]
  val cityInhabitants = allCities map inhabitants;System.out.println("""cityInhabitants  : scalaz.Kleisli[List,String,Int] = """ + $show(cityInhabitants ));$skip(44); val res$5 = 
  (cityInhabitants) =<< List("Amer", "Asi");System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(226); 

  // with mapK you can map a kleisli function into
  // another monadic structure, e.g. provide a function
  // M[A] => N[B]
  // Note : the example is not particularily useful here.
  val getandSave = (allCities mapK save);System.out.println("""getandSave  : scalaz.Kleisli[scala.util.Try,String,Unit] = """ + $show(getandSave ));$skip(37); val res$6 = 
  getandSave("America").map(println);System.out.println("""res6: scala.util.Try[Unit] = """ + $show(res$6));$skip(219); 

  // local can be used to prepend a kleisli function of
  // the form A => M[B] with a function of the form
  // AA => A, resulting in a kleisli function of the form
  // AA => M[B]
  def index(i: Int) = data(i).name;System.out.println("""index: (i: Int)String""");$skip(47); 
  val allCitiesByIndex = allCities local index;System.out.println("""allCitiesByIndex  : scalaz.Kleisli[List,Int,scalaz.stuff.kleisli_testing.City] = """ + $show(allCitiesByIndex ));$skip(35); val res$7 = 
  allCitiesByIndex(1).map(println);System.out.println("""res7: List[Unit] = """ + $show(res$7))}
}
