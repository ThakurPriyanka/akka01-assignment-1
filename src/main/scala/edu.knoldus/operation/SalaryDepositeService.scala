package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import edu.knoldus.akkaData.BillList
import edu.knoldus.operation.PayBill.CheckBill
import edu.knoldus.util.{Account, AccountOperations}

import scala.collection.mutable.Map


object SalaryDepositeService {
  def props(accountList: Map[String, Account], billList: Map[String, AnyRef], system: ActorSystem): Props =
    Props(new SalaryDepositeService(accountList, billList, system))

  final case class AddSalary(userName: String, accountNumber: String, salary: Double)

}

class SalaryDepositeService(accountList: Map[String, Account], billList: Map[String, AnyRef], system: ActorSystem) extends Actor with ActorLogging {

  import SalaryDepositeService._

  val accountOperation = new AccountOperations
  billList: Map[String, AnyRef]

  override def preStart(): Unit = log.info(s"Salary Depositor actor  started")

  override def postStop(): Unit = log.info(s"Salary Depositor actor  stopped")

  override def receive: Receive = {
    case AddSalary(userName, accountNumber, salary) => if (accountList.contains(userName)) {
      if (accountList(userName).accountNumber == accountNumber) {
        val newAccount = accountOperation.addSalary(salary, accountList(userName))
        accountList(userName) = newAccount
        val payBill = system.actorOf(PayBill.props(billList, accountList, system), "Paying-bill")
        payBill ! CheckBill(accountNumber, userName)
      }
    }
    else {
      throw new Exception("can not find the account")
    }
  }
}

