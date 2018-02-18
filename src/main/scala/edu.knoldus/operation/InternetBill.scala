package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.akkaData.BillList
import edu.knoldus.util.{Account, AccountOperations, Bill, BillOperation}

import scala.collection.mutable.Map

object InternetBill {

  def props(accountList: Map[String, Account], billList: Map[String, AnyRef]): Props = Props(new InternetBill(accountList, billList))

  case class InBillPaid(accountNumber: String, userName: String, bill: Bill)

}

class InternetBill(accountList: Map[String, Account], billList: Map[String, AnyRef]) extends Actor with ActorLogging {

  import InternetBill._

  override def preStart(): Unit = log.info("Internet Bill started")

  override def postStop(): Unit = log.info("Internet Bill stopped")

  val operationAccount = new AccountOperations
  val operationBill = new BillOperation

  override def receive: Receive = {
    case InBillPaid(accountNumber, userName, bill) => if (accountList(userName).isInstanceOf[Account]) {
      val amount = bill.amount
      accountList(userName) = operationAccount.updateAccount(amount, accountList(userName))
      BillList.billList(accountNumber) = operationBill.updateBill(bill)
    }
  }
}
