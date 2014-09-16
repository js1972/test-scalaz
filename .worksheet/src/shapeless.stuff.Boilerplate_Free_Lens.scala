package shapeless.stuff

import shapeless._

object Boilerplate_Free_Lens {

  // A pair of ordinary case classes ...
  case class Address(street : String, city : String, postcode : String)
  case class Person(name : String, age : Int, address : Address);import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(341); 
  
  // Some lenses over Person/Address ...
  val nameLens     = lens[Person] >> 'name;System.out.println("""nameLens  : <error> = """ + $show(nameLens ));$skip(42); 
  val ageLens      = lens[Person] >> 'age;System.out.println("""ageLens  : <error> = """ + $show(ageLens ));$skip(46); 
  val addressLens  = lens[Person] >> 'address;System.out.println("""addressLens  : <error> = """ + $show(addressLens ));$skip(57); 
  val streetLens   = lens[Person] >> 'address >> 'street;System.out.println("""streetLens  : <error> = """ + $show(streetLens ));$skip(55); 
  val cityLens     = lens[Person] >> 'address >> 'city;System.out.println("""cityLens  : <error> = """ + $show(cityLens ));$skip(59); 
  val postcodeLens = lens[Person] >> 'address >> 'postcode;System.out.println("""postcodeLens  : <error> = """ + $show(postcodeLens ));$skip(96); 
  
  
  val person = Person("Joe Grey", 37, Address("Southover Street", "Brighton", "BN2 9UA"));System.out.println("""person  : shapeless.stuff.Boilerplate_Free_Lens.Person = """ + $show(person ));$skip(36); 
  
  val age1 = ageLens.get(person);System.out.println("""age1  : <error> = """ + $show(age1 ));$skip(40); 
  val person2 = ageLens.set(person)(38);System.out.println("""person2  : <error> = """ + $show(person2 ))}
}
