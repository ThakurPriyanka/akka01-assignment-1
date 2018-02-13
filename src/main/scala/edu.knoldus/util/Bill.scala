package edu.knoldus.util

case class Bill(billerCategory: String, billerName: String, accountNumber: String,
                transactionDate: String, amount: Double, totalIterations: Int, executedIterations: Int, paidAmount: Double)
