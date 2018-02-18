package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import edu.knoldus.operation.CarBill.CarBillPaid
import edu.knoldus.operation.ElectricityBill.ElBillPaid
import edu.knoldus.operation.FoodBill.FoBillPaid
import edu.knoldus.operation.InternetBill.InBillPaid
import edu.knoldus.operation.PhoneBill.PhBillPaid
import edu.knoldus.util.{Account, Bill}

import scala.collection.mutable.Map

object PayBill {
  def props(billList: Map[String, AnyRef], accountList: Map[String, Account], system: ActorSystem): Props = Props(new PayBill(billList, accountList, system))

  case class CheckBill(accountNumber: String, userName: String)

}

class PayBill(billList: Map[String, AnyRef], accountList: Map[String, Account], system: ActorSystem) extends Actor with ActorLogging {

  import PayBill._

  val phoneBill = system.actorOf(PhoneBill.props(accountList, billList), "Paying-phone-bill")
  val electricityBill = system.actorOf(ElectricityBill.props(accountList, billList), "Paying-electricity-bill")
  val carBill = system.actorOf(CarBill.props(accountList, billList), "Paying-car-bill")
  val foodBill = system.actorOf(FoodBill.props(accountList, billList), "Paying-food-bill")
  val internetBill = system.actorOf(InternetBill.props(accountList, billList), "Paying-internet-bill")

  override def receive: Receive = {
    case CheckBill(accountNumber, userName) if (billList(accountNumber).isInstanceOf[Bill]) =>
        val bill = billList(accountNumber).asInstanceOf[Bill]
      bill.billerCategory match {
        case "phone" => phoneBill ! PhBillPaid(accountNumber, userName, bill)
        case "electricity" => electricityBill ! ElBillPaid(accountNumber, userName, bill)
        case "internet" => internetBill ! InBillPaid(accountNumber,userName, bill)
        case "food" => foodBill ! FoBillPaid(accountNumber,userName, bill)
        case "car" => carBill ! CarBillPaid(accountNumber,userName, bill)
      }
    case CheckBill(accountNumber, userName) if !billList(accountNumber).isInstanceOf[Bill] =>
      val listOfBill = billList(accountNumber).asInstanceOf[List[Bill]]
      listOfBill.map((bill) => bill.billerCategory match {
        case "phone" => phoneBill ! PhBillPaid(accountNumber, userName, bill)
        case "electricity" => electricityBill ! ElBillPaid(accountNumber, userName, bill)
        case "internet" => internetBill ! InBillPaid(accountNumber,userName, bill)
        case "food" => foodBill ! FoBillPaid(accountNumber,userName, bill)
        case "car" => carBill ! CarBillPaid(accountNumber,userName, bill)
      } )
  }
}
