package scalaz.endo.stuff

import scalaz._
import Scalaz._
import java.util.{Date, Calendar}

// these are for testing out the Play json pretty print
import play.api.libs.json._
import play.api.libs.functional._
// this one is an extension to play-json to handle class hierarchies from a sealed trait
import julienrf.variants.Variants


/**
 * Another example of composing operations using a fluent api provided by endo and monoid.
 * This time we define a security which must go through a number of transformations for 
 * a trade: validate, addValueDate, enrich and journalize.
 */
object EndoFluent {
  sealed trait Instrument
  case class Security(isin: String, name: String) extends Instrument

  case class Trade(refNo: String, tradeDate: Date, valueDate: Option[Date] = None, 
    ins: Instrument, principal: BigDecimal, net: Option[BigDecimal] = None, status: TradeStatus = CREATED)
  
  sealed trait TradeStatus
  case object CREATED extends TradeStatus
  case object FINALIZED extends TradeStatus
  case object VALUE_DATE_ADDED extends TradeStatus
  case object ENRICHED extends TradeStatus
  case object VALIDATED extends TradeStatus

  type TradeLifecycle = Endo[Trade]

  // validate the trade: business logic elided
  def validate: TradeLifecycle = 
    ((t: Trade) => t.copy(status = VALIDATED)).endo

  // add value date to the trade (for settlement)
  def addValueDate: TradeLifecycle = 
    ((t: Trade) => t.copy(valueDate = Some(t.tradeDate), status = VALUE_DATE_ADDED)).endo

  // enrich the trade: add taxes and compute net value: business logic elided
  def enrich: TradeLifecycle = 
    ((t: Trade) => t.copy(net = Some(t.principal + 100), status = ENRICHED)).endo

  // journalize the trade into book: business logic elided
  def journalize: TradeLifecycle = 
    ((t: Trade) => t.copy(status = FINALIZED)).endo

    
  def doTrade(t: Trade) =
    (journalize |+| enrich |+| addValueDate |+| validate).run(t) //apply(t)
    
    
  def main(args: Array[String]): Unit = {
    val now = Calendar.getInstance().getTime()
    val t = doTrade(Trade("12345", now, None, Security("GOOG", "Google Inc."), 1000))
    println(t)
    
    
    // Print out the trade in json format - easier to visualise
    implicit val instrumentFormat: Format[Instrument] = Variants.format[Instrument]
    implicit val tradeStatusFormat: Format[TradeStatus] = Variants.format[TradeStatus]
    implicit val tradeFormat = Json.format[Trade]
    println
    println(Json.prettyPrint(Json.toJson(t)))
  }
}