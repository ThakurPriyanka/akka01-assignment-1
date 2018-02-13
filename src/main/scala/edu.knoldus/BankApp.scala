package edu.knoldus

import akka.actor.ActorSystem
import edu.knoldus.operation.{BankAccountGenerator, BankService, SalaryDepositeService}
import edu.knoldus.util.Account

import scala.io.StdIn

object BankApp {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("bank-system")

    try {

      val salary1 = 12000
      val salary2 = 12000
      val account1 = Account("123", "priyanka",  "Priyanka", "Gtb nagar", salary1)

      val account2 = Account("124", "priyank",  "Priyanka", "Gtb nagar", salary2)
      val accountList = scala.collection.mutable.Map.empty[String, Account]
      val generator = system.actorOf(BankAccountGenerator.props(accountList), "Bank-account-generator")
      val salaryDepositor = system.actorOf(SalaryDepositeService.props(accountList), "Salary-Depositor")
      val supervisor = system.actorOf(BankService.props(generator, salaryDepositor), "Bank-supervisor")

      supervisor ! BankService.NewAccount(account1)
      supervisor ! BankService.NewAccount(account2)

      val userName = "priyanka"
      val accountNumber = "123"
      val salary = 1000
      supervisor ! BankService.UpdateAccount(userName, accountNumber, salary)
      val timeToSleep = 10000
      Thread.sleep(timeToSleep)
      print(accountList)
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }

}

