package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import edu.knoldus.operation.BankAccountGenerator.AddAccount
import edu.knoldus.operation.BillLinkAccount.Link
import edu.knoldus.operation.SalaryDepositeService.AddSalary
import edu.knoldus.util.{Account, Bill}
import scala.collection.mutable.Map

object BankService {
  def props(generatorAccount: ActorRef, depositeSalary: ActorRef, billAccountLinker: ActorRef, billList: Map[String, AnyRef]): Props =
    Props(new BankService(generatorAccount, depositeSalary, billAccountLinker, billList))

  case class NewAccount(account: Account)

  case class UpdateAccount(userName: String, accountNumber: String, salary: Double)

  case class AddBillAccount(accountNumber: String, bill: Bill)

}

class BankService(generatorAccount: ActorRef, depositeSalary: ActorRef, billAccountLinker: ActorRef,
                  billList: Map[String, AnyRef]) extends Actor with ActorLogging {

  import BankService._

  override def preStart(): Unit = log.info("BankService Application started")

  override def postStop(): Unit = log.info("BankService Application stopped")


  override def receive: Receive = {
    case NewAccount(account) => generatorAccount ! AddAccount(account)
    case UpdateAccount(userName, accountNumber, salary) => depositeSalary ! AddSalary(userName, accountNumber, salary)
    case AddBillAccount(accountNumber, bill) =>  billAccountLinker ! Link(accountNumber, bill)
  }

}

