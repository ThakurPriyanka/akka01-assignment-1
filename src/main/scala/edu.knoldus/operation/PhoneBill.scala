package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.akkaData.BillList
import edu.knoldus.util.{Account, AccountOperations, Bill, BillOperation}

import scala.collection.mutable.Map

object PhoneBill {

  def props(accountList: Map[String, Account], billList: Map[String, AnyRef]): Props = Props(new PhoneBill(accountList, billList))

  case class PhBillPaid(accountNumber: String, userName: String, bill: Bill)

}

class  PhoneBill(accountList: Map[String, Account], billList: Map[String, AnyRef]) extends Actor with ActorLogging {

  import  PhoneBill._

  val operationAccount = new AccountOperations
  val operationBill = new BillOperation

  override def receive: Receive = {
    case PhBillPaid(accountNumber, userName, bill) => if (accountList(userName).isInstanceOf[Account]) {
      val amount = bill.amount
      accountList(userName) = operationAccount.updateAccount(amount, accountList(userName))
      BillList.billList(accountNumber) = operationBill.updateBill(bill)
    }
  }
}
