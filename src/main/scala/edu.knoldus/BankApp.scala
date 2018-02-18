package edu.knoldus

import edu.knoldus.akkaData.{AccountList, BankSystem, BillList}
import edu.knoldus.operation.{BankAccountGenerator, BankService, BillLinkAccount, SalaryDepositeService}
import edu.knoldus.util.{Account, Bill}
import org.apache.log4j.Logger

import scala.io.StdIn

object BankApp {

  def main(args: Array[String]): Unit = {
    val log =  Logger.getLogger(this.getClass)
    val system = BankSystem.system
    try {

      val salary1 = 12000
      val salary2 = 12000
      val account1 = Account("123", "priyanka", "Priyanka", "Gtb nagar", salary1)

      val account2 = Account("124", "priyank", "Priyanka", "Gtb nagar", salary2)
      val bill1 = Bill("food", "priyanka", "123", "12-03-2017", 120, 1, 1, 120)
      val bill2 = Bill("phone", "priyanka", "123", "12-03-2017", 120, 1, 1, 120)
      val bill3 = Bill("electricity", "priyanka", "124", "12-03-2017", 120, 1, 1, 120)
      val bill4 = Bill("food", "priyanka", "124", "12-03-2017", 120, 1, 1, 120)
      val accountList = AccountList.accountList
      val billList = BillList.billList
      val generator = system.actorOf(BankAccountGenerator.props(accountList), "Bank-account-generator")
      val salaryDepositor = system.actorOf(SalaryDepositeService.props(accountList, billList, system), "Salary-Depositor")
      val billAccountLinker = system.actorOf(BillLinkAccount.props(billList), "Bill-Linker-Account")
      val supervisor = system.actorOf(BankService.props(generator, salaryDepositor, billAccountLinker, billList), "Bank-supervisor")

      supervisor ! BankService.NewAccount(account1)
      supervisor ! BankService.NewAccount(account2)
      supervisor ! BankService.AddBillAccount("123", bill1)
      supervisor ! BankService.AddBillAccount("123", bill2)
      supervisor ! BankService.AddBillAccount("124", bill3)
      supervisor ! BankService.AddBillAccount("124", bill4)

      val userName = "priyanka"
      val accountNumber = "123"
      val salary = 1000
      supervisor ! BankService.UpdateAccount(userName, accountNumber, salary)
      val timeToSleep = 10000
      Thread.sleep(timeToSleep)
      log.info(s"Account List ${accountList} \n")
      log.info(s"Bill List ${billList} \n")
      log.info(s"New AccountList: ${accountList} \n")
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }

}

