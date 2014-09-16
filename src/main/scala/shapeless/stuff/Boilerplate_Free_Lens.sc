package shapeless.stuff

import shapeless._

object Boilerplate_Free_Lens {

  // A pair of ordinary case classes ...
  case class Address(street : String, city : String, postcode : String)
  case class Person(name : String, age : Int, address : Address)
  
  // Some lenses over Person/Address ...
  val nameLens     = lens[Person] >> 'name        //> nameLens  : <error> = shapeless.Lens$$anon$6@1f67e563
  val ageLens      = lens[Person] >> 'age         //> ageLens  : <error> = shapeless.Lens$$anon$6@53622a11
  val addressLens  = lens[Person] >> 'address     //> addressLens  : <error> = shapeless.Lens$$anon$6@1cb3a963
  val streetLens   = lens[Person] >> 'address >> 'street
                                                  //> streetLens  : <error> = shapeless.Lens$$anon$6@57a65cf1
  val cityLens     = lens[Person] >> 'address >> 'city
                                                  //> cityLens  : <error> = shapeless.Lens$$anon$6@5fe2d461
  val postcodeLens = lens[Person] >> 'address >> 'postcode
                                                  //> postcodeLens  : <error> = shapeless.Lens$$anon$6@34a17d9
  
  
  val person = Person("Joe Grey", 37, Address("Southover Street", "Brighton", "BN2 9UA"))
                                                  //> person  : shapeless.stuff.Boilerplate_Free_Lens.Person = Person(Joe Grey,37,
                                                  //| Address(Southover Street,Brighton,BN2 9UA))
  
  val age1 = ageLens.get(person)                  //> age1  : <error> = 37
  val person2 = ageLens.set(person)(38)           //> person2  : <error> = Person(Joe Grey,38,Address(Southover Street,Brighton,BN
                                                  //| 2 9UA))
}