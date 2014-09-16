package shapeless.stuff

import shapeless._

/**
 * Shapeless has a Coproduct type, a generalization of Scala's Either to an arbitrary number of choices.
 * Currently it exists primarily to support Generic (see the next section), but will be expanded
 * analogously to HList in later releases. Currently Coproduct supports mapping, selection and unification.
 *
 * See: https://github.com/milessabin/shapeless/wiki/Feature-overview:-shapeless-2.0.0
 */
object Coproducts_Either_DiscriminatedUnion {
  type ISB = Int :+: String :+: Boolean :+: CNil
  val isb = Coproduct[ISB]("foo")                 //> isb  : shapeless.stuff.Coproducts_Either_DiscriminatedUnion.ISB = foo
  
  isb.select[Int]                                 //> res0: Option[Int] = None
  isb.select[String]                              //> res1: Option[String] = Some(foo)
  
  
  // size is a function from Ints, Strings or Booleans
  object size extends Poly1 {
    implicit def caseInt = at[Int](i => (i, i))
    implicit def caseString = at[String](s => (s, s.length))
    implicit def caseBoolean = at[Boolean](b => (b, 1))
  }
  
  val r = isb map size                            //> r  : shapeless.:+:[(Int, Int),shapeless.:+:[(String, Int),shapeless.:+:[(Boo
                                                  //| lean, Int),shapeless.CNil]]] = (foo,3)
  r.select[(String, Int)]                         //> res2: Option[(String, Int)] = Some((foo,3))
  
  
  
  // In the same way that adding labels to the elements of an HList gives us a record,
  // adding labels to the elements of a Coproduct gives us a discriminated union,
  import record.RecordType, syntax.singleton._, union._
  val uSchema = RecordType.like('i ->> 23 :: 's ->> "foo" :: 'b ->> true :: HNil)
                                                  //> uSchema  : <error> = shapeless.record$RecordType$$anon$2@6ab191cd
  type U = uSchema.Union
  
  val u = Coproduct[U]('s ->> "foo")              //> u  : <error> = foo
  
  u.get('i)                                       //> res3: <error> = None
  u.get('s)                                       //> res4: <error> = Some(foo)
  u.get('b)                                       //> res5: <error> = None
}