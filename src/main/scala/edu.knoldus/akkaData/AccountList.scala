package edu.knoldus.akkaData

import edu.knoldus.util.Account

object AccountList {
  val accountList = scala.collection.mutable.Map.empty[String, Account]
}
