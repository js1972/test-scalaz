package scalaz.reader.stuff

/**
 * The Specification Pattern: implementing domain validation.
 * This is a technique for validating domain model data by chaining
 * together a series of validations (or specifications). 
 * See this blog post for details:
 * http://debasishg.blogspot.com.au/2014/03/functional-patterns-in-domain-modeling.html
 * 
 * You will note below that scalaz provides the Kleisli object as used below in the
 * "validate" (specification functions) function: Kleisli[ValidationStatus, Order, Boolean].
 * Alternatively, we could create our own Kleisli type ReaderTStatus and implementation
 * object.
 * Which is better/simpler? Using the scalaz provided Kleisli object is a few less
 * lines of code - you just need to keep providing the extra type parameter. Personally
 * I'm leaning towards how I have it below - using the Kleisli object.
 */

import scalaz._
import Scalaz._
import \/._
import java.util.{Date, Calendar}


object Specifications {
  
  // Setup some types: We are dealing with an Either for the validation, but so that
  // we don't have to continually pass the data between functions we use a Reader
  // monad. But we already have the Either monad so we need to stack them together
  // using ReaderT which is the Reader monad transformer.
  // If we were wanting a plain Reader we could have just use the Reader object
  // which is provided by scalaz instead of creating the ReaderTStatus object below.
  // We use a Kleisli to implement ReaderTStatus as scalaz does.
  
  type ValidationStatus[S] = \/[String, S]
  //type ReaderTStatus[A, S] = ReaderT[ValidationStatus, A, S]
  
  //object ReaderTStatus extends KleisliInstances with KleisliFunctions {
  //  def apply[A, S](f: A => ValidationStatus[S]): ReaderTStatus[A, S] = kleisli(f)
  //}
  
  
  // Domain model classes
  
  sealed trait Item {
    def itemCode: String
  }
  case class ItemA(itemCode: String, desc: Option[String], minPurchaseUnit: Int) extends Item
  case class ItemB(itemCode: String, desc: Option[String], nutritionInfo: String) extends Item

  case class LineItem(item: Item, quantity: Int)

  case class Order(orderNo: String, orderDate: Date, customer: Customer, lineItems: List[LineItem])

  case class Customer(custId: String, name: String, category: Int)
  
  
  /**
   * Execute the validations - as we are using an Either it will fail on any one validation failure.
   * Note how we don't need to pass the order around to each validation as its wrapped in the Reader.
   * This results is now fully composable and can be chained with further processing
   */
  def isReadyForFulfilment(order: Order) = {
    val s = for {

      _ <- validate
      _ <- approve
      _ <- checkCustomerStatus(order.customer)
      c <- checkInventory

    } yield c
    
    // The order parameter is curried and passed here...
    s(order)
  }

  
  // The validations to process - note that the order parameter is curried, so these
  // functions pass a functions (requiring the order as a parameter) to ReaderTStatus
  // which is executed later with the order parameter...
  // This follows on from the fact that Reader is just a monad over a function!
  
  //private def validate = ReaderTStatus[Order, Boolean] {order =>
  private def validate = Kleisli[ValidationStatus, Order, Boolean] {order => 
    if (order.lineItems isEmpty) left(s"Validation failed for order $order") else right(true)
  }

  //private def approve = ReaderTStatus[Order, Boolean] {order =>
  private def approve = Kleisli[ValidationStatus, Order, Boolean] {order => 
    println("approved")
    right(true)
  }

  //private def checkCustomerStatus(customer: Customer) = ReaderTStatus[Order, Boolean] {order =>
  private def checkCustomerStatus(customer: Customer) = Kleisli[ValidationStatus, Order, Boolean] {order => 
    right(true)
  }

  //private def checkInventory = ReaderTStatus[Order, Boolean] {order =>
  private def checkInventory = Kleisli[ValidationStatus, Order, Boolean] {order => 
    println("inventory checked")
    right(true)
  }
  
  
  // Main program to test...
  
  def main(args: Array[String]): Unit = {
    val now = Calendar.getInstance().getTime()
    
    println( isReadyForFulfilment(Order("123456", now, Customer("Cust_1", "Customer 1", 1), List(LineItem(ItemB("Item B", "This is item B".some, "Junk"), 5)))) )
    println
    println( isReadyForFulfilment(Order("999999", now, Customer("Cust_2", "Customer 2", 2), List())) )
  }
}