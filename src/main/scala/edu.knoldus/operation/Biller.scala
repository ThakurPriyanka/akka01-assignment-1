package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import edu.knoldus.util.{Account, Bill}

import scala.collection.mutable.Map

object  Biller  {

  def props(accountList: Map[String, Account]): Props = Props(new Biller(accountList))

  final case class PhoneBill(accountNumber: String, bill: Bill)
  final case class ElecticityBill(accountNumber: String, bill: Bill)
  final case class InternetBill(accountNumber: String, bill: Bill)
  final case class FoodBill(accountNumber: String, bill: Bill)
  final case class CarBill(accountNumber: String, bill: Bill)

}


class Biller(accountList: Map[String, Account]) extends Actor with ActorLogging {
  import Biller._

  override def receive : Receive = {
    case PhoneBill(accountNumber, bill) =>
    case ElecticityBill(accountNumber, bill) =>
    case InternetBill(accountNumber, bill) =>
    case FoodBill(accountNumber, bill) =>
    case CarBill(accountNumber, bill) =>
  }

}
