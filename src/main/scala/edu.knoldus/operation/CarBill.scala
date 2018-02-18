package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.akkaData.BillList
import edu.knoldus.util.{Account, AccountOperations, Bill, BillOperation}

import scala.collection.mutable.Map

object CarBill {
  def props(accountList: Map[String, Account], billList: Map[String, AnyRef]): Props = Props(new CarBill(accountList, billList))

  case class CarBillPaid(accountNumber: String, userName: String, bill: Bill)

}

class CarBill(accountList: Map[String, Account], billList: Map[String, AnyRef]) extends Actor with ActorLogging {

  import CarBill._

  override def preStart(): Unit = log.info("Car Bill started")

  override def postStop(): Unit = log.info("Car Bill stopped")

  val operationAccount = new AccountOperations
  val operationBill = new BillOperation

  override def receive : Receive = {
    case CarBillPaid(accountNumber,userName, bill) => if (accountList(userName).isInstanceOf[Account]) {
      val amount = bill.amount
      accountList(userName) = operationAccount.updateAccount(amount, accountList(userName))
      BillList.billList(accountNumber) = operationBill.updateBill(bill)
    }
  }
}
