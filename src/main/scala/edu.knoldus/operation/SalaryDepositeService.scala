package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.util.{Account, AccountOperations}

import scala.collection.mutable.Map


object  SalaryDepositeService  {
  def props(accountList: Map[String, Account]): Props = Props(new SalaryDepositeService(accountList))

  final case class AddSalary(userName: String, accountNumber: String, salary: Double)
}

class SalaryDepositeService(accountList: Map[String, Account]) extends Actor with ActorLogging {
  import SalaryDepositeService._

  val accountOperation = new AccountOperations

  override def preStart(): Unit = log.info(s"Salary Depositor actor  started")
  override def postStop(): Unit = log.info(s"Salary Depositor actor  stopped")

  override def receive: Receive = {
    case AddSalary(userName, accountNumber, salary) => if (accountList.contains(userName)) {
      if (accountList(userName).accountNumber == accountNumber) {
        val newAccount = accountOperation.addSalary(salary, accountList(userName))
        accountList(userName) = newAccount
      }
    }
    else {
      throw new Exception("can not find the account")
    }
  }
}

