package edu.knoldus.util

class BillOperation {


  def updateBill(bill: Bill): Bill = {
    if (bill.executedIterations > bill.totalIterations) {
      throw new Exception("Bill Paid.")
    }
    else if (bill.executedIterations <= bill.totalIterations) {
      val newExecutedIteration = bill.executedIterations + 1
      val newPaidAmount = bill.paidAmount - bill.amount
      val newBill = bill.copy(executedIterations = newExecutedIteration, amount = newPaidAmount)
      newBill
    }
    else {
      throw new Exception("Can not process the transaction.")
    }
  }

}
