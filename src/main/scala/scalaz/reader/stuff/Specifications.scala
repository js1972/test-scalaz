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
 * 
 * Added:
 * Lens definitions for update of the data model
 * Processing functions and composition of these functions
 * See blog post for details:
 * http://debasishg.blogspot.com.au/2014/05/functional-patterns-in-domain-modeling.html
 */ 

import scalaz._
import Scalaz._
import \/._
import PLens._
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
  type ProcessingStatus[S] = \/[String, S]
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

  case class LineItem(item: Item, quantity: BigDecimal, value: Option[BigDecimal] = None, discount: Option[BigDecimal] = None)

  case class Customer(custId: String, name: String, category: Int)

  sealed trait OrderStatus
  case object Placed extends OrderStatus
  case object Validated extends OrderStatus

  case class Address(number: String, street: String, city: String, zip: String)
  case class ShipTo(name: String, address: Address)

  case class Order(orderNo: String, orderDate: Date, customer: Customer, 
    lineItems: Vector[LineItem], shipTo: ShipTo, netOrderValue: Option[BigDecimal] = None, status: OrderStatus = Placed)
  
  
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
  
  
  
  /**
   * lens definitions for update of aggregate root
   */
  val orderStatus = Lens.lensu[Order, OrderStatus] (
    (o, value) => o.copy(status = value),
    _.status
  )

  val orderLineItems = Lens.lensu[Order, Vector[LineItem]] (
    (o, lis) => o.copy(lineItems = lis),
    _.lineItems
  )

  val lineItemValue = Lens.lensu[LineItem, Option[BigDecimal]] (
    (l, v) => l.copy(value = v),
    _.value
  )

  val lineItemDiscount = Lens.lensu[LineItem, Option[BigDecimal]] (
    (l, value) => l.copy(discount = value),
    _.discount
  )

  def lineItemValues(i: Int) = ~lineItemValue compose vectorNthPLens(i)
  def lineItemDiscounts(i: Int) = ~lineItemDiscount compose vectorNthPLens(i)

  val orderShipTo = Lens.lensu[Order, ShipTo] (
    (o, sh) => o.copy(shipTo = sh),
    _.shipTo
  )

  val shipToAddress = Lens.lensu[ShipTo, Address] (
    (sh, add) => sh.copy(address = add),
    _.address
  )

  val addressToCity = Lens.lensu[Address, String] (
    (add, c) => add.copy(city = c),
    _.city
  )

  def orderShipToCity = orderShipTo andThen shipToAddress andThen addressToCity
  
  
  // Processing functions: valueOrder, applyDiscounts, checkout
  
  def valueOrder = Kleisli[ProcessingStatus, Order, Order] {order =>
    val o = orderLineItems.set(
      order,
      setLineItemValues(order.lineItems)
    )
    o.lineItems.map(_.value).sequenceU match {
      case Some(_) => right(o)
      case _ => left("Missing value for items")
    }
  }
  
  private def setLineItemValues(lis: Vector[LineItem]) = {
    (0 to lis.length - 1).foldLeft(lis) {(s, i) => 
      val li = lis(i)
      lineItemValues(i).set(s, unitPrice(li.item).map(_ * li.quantity)).getOrElse(s)
    }
  }
  
  def applyDiscounts = Kleisli[ProcessingStatus, Order, Order] {order =>
    val o = orderLineItems.set(
      order,
      setLineItemValues(order.lineItems)
    )
    o.lineItems.map(_.discount).sequenceU match {
      case Some(_) => right(o)
      case _ => left("Missing discount for items")
    }
  }
  
  private def setLineItemDiscounts(lis: Vector[LineItem], customer: Customer) = {
    (0 to lis.length - 1).foldLeft(lis) {(s, i) => 
      val li = lis(i)
      lineItemDiscounts(i).set(s, discount(li.item, customer)).getOrElse(s)
    }
  }
  
  val orderNetValue = Lens.lensu[Order, Option[BigDecimal]] (
    (o, v) => o.copy(netOrderValue = v),
    _.netOrderValue
  )
  
  def checkOut = Kleisli[ProcessingStatus, Order, Order] {order =>

    val netOrderValue = order.lineItems.foldLeft(BigDecimal(0).some) {(s, i) => 
      s |+| (i.value |+| i.discount.map(d => Tag.unwrap(Tags.Multiplication(BigDecimal(-1)) |+| Tags.Multiplication(d))))
    }
    right(orderNetValue.set(order, netOrderValue))
  }
  
  private def unitPrice(item: Item): Option[BigDecimal] = {
    BigDecimal(12).some
  }

  private def discount(item: Item, customer: Customer) = {
    BigDecimal(5).some
  }
  
  
  
  def process(order: Order) = {
    // =<< is defined as def =<<(a: M[A])(implicit m: Bind[M]): M[B] = m.bind(a)(run) in Kleisli.scala
    (valueOrder andThen applyDiscounts andThen checkOut) =<< right(orderStatus.set(order, Validated))
  }
  
  
  // Main program to test...
  
  def main(args: Array[String]): Unit = {
    val now = Calendar.getInstance().getTime()
    
    val o = Order("123456", now, Customer("Cust_1", "Customer 1", 1), Vector(LineItem(ItemB("Item B", "This is item B".some, "Junk"), 5, (10: BigDecimal).some, (2: BigDecimal).some)), ShipTo("ShipTo 1", Address("1", "street", "city", "1234")))
    println("Fulfilment check:")
    println(isReadyForFulfilment(o))
    println
    println("Process order:")
    println(process(o))
    
    println
    
    val o2 = Order("123456", now, Customer("Cust_1", "Customer 1", 1), Vector(LineItem(ItemB("Item B", "This is item B".some, "Junk"), 5)), ShipTo("ShipTo 1", Address("1", "street", "city", "1234")))
    println("Fulfilment check:")
    println(isReadyForFulfilment(o2))
    println
    println("Process order:")
    println(process(o2))
    
    //println( isReadyForFulfilment(Order("123456", now, Customer("Cust_1", "Customer 1", 1), List(LineItem(ItemB("Item B", "This is item B".some, "Junk"), 5)))) )
    //println
    //println( isReadyForFulfilment(Order("999999", now, Customer("Cust_2", "Customer 2", 2), List())) )
  }
}