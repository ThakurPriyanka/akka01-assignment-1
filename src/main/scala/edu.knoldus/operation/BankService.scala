package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import edu.knoldus.operation.BankAccountGenerator.AddAccount
import edu.knoldus.operation.SalaryDepositeService.AddSalary
import edu.knoldus.util.Account

object BankService {
    def props(generatorAccount: ActorRef, depositeSalary: ActorRef): Props = Props(new BankService(generatorAccount,  depositeSalary))
    case class NewAccount(account: Account)
    case class UpdateAccount(userName: String, accountNumber: String, salary: Double)
  }

class BankService(generatorAccount: ActorRef, depositeSalary: ActorRef) extends Actor with ActorLogging {
    import BankService._
    override def preStart(): Unit = log.info("BankService Application started")
    override def postStop(): Unit = log.info("BankService Application stopped")

    // No need to handle any messages
    override def receive: Receive = {
    case NewAccount(account) => generatorAccount ! AddAccount(account)
    case UpdateAccount(userName, accountNumber, salary) => depositeSalary ! AddSalary(userName, accountNumber, salary)
  }

}

