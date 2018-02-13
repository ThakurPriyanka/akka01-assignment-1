package edu.knoldus.util

class AccountOperations {

  def addSalary(salary: Double, account: Account): Account = {
    val newAmount = account.amount + salary
    account.copy(amount =  newAmount)
  }

  def updateAccount(amountToDeduct: Double, account: Account): Account = {
    val newAmount = account.amount- amountToDeduct
    if(newAmount > 0) {
      account.copy(amount = newAmount)
    }
    else {
      throw new Exception("Can not process the transaction.")
    }
  }

}
