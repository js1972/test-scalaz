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
  type ISB = Int :+: String :+: Boolean :+: CNil;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(581); 
  val isb = Coproduct[ISB]("foo");System.out.println("""isb  : shapeless.stuff.Coproducts_Either_DiscriminatedUnion.ISB = """ + $show(isb ));$skip(21); val res$0 = 
  
  isb.select[Int];System.out.println("""res0: Option[Int] = """ + $show(res$0));$skip(21); val res$1 = 
  isb.select[String]
  
  
  // size is a function from Ints, Strings or Booleans
  object size extends Poly1 {
    implicit def caseInt = at[Int](i => (i, i))
    implicit def caseString = at[String](s => (s, s.length))
    implicit def caseBoolean = at[Boolean](b => (b, 1))
  };System.out.println("""res1: Option[String] = """ + $show(res$1));$skip(286); 
  
  val r = isb map size;System.out.println("""r  : shapeless.:+:[(Int, Int),shapeless.:+:[(String, Int),shapeless.:+:[(Boolean, Int),shapeless.CNil]]] = """ + $show(r ));$skip(26); val res$2 = 
  r.select[(String, Int)]
  
  
  
  // In the same way that adding labels to the elements of an HList gives us a record,
  // adding labels to the elements of a Coproduct gives us a discriminated union,
  import record.RecordType, syntax.singleton._, union._;System.out.println("""res2: Option[(String, Int)] = """ + $show(res$2));$skip(316); 
  val uSchema = RecordType.like('i ->> 23 :: 's ->> "foo" :: 'b ->> true :: HNil)
  type U = uSchema.Union;System.out.println("""uSchema  : <error> = """ + $show(uSchema ));$skip(65); 
  
  val u = Coproduct[U]('s ->> "foo");System.out.println("""u  : <error> = """ + $show(u ));$skip(15); val res$3 = 
  
  u.get('i);System.out.println("""res3: <error> = """ + $show(res$3));$skip(12); val res$4 = 
  u.get('s);System.out.println("""res4: <error> = """ + $show(res$4));$skip(12); val res$5 = 
  u.get('b);System.out.println("""res5: <error> = """ + $show(res$5))}
}
