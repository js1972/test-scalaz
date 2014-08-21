package testing

//trait Monoid[A] {
//  def mappend(a: A, b: A): A
//  def mzero: A
//}

object IntMonoid {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(151); 
  def mappend(a: Int, b: Int): Int = a + b;System.out.println("""mappend: (a: Int, b: Int)Int""");$skip(21); 
  def mzero: Int = 0;System.out.println("""mzero: => Int""")}
}

object monoids {
  println("Welcome to the Scala worksheet")

  def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)
  
  sum(List(1, 2, 3, 4))
}
