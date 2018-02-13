package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.util.Account

import scala.collection.mutable.Map

object  BankAccountGenerator  {
    def props(accountList: Map[String, Account]): Props = Props(new BankAccountGenerator(accountList))

    final case class AddAccount(account: Account)
  }

class BankAccountGenerator(accountList: Map[String, Account]) extends Actor with ActorLogging {
    import BankAccountGenerator._


    override def preStart(): Unit = log.info(s"BankAccountGenerator actor  started")
    override def postStop(): Unit = log.info(s"BankAccountGenerator actor  stopped")

    override def receive: Receive = {
      case  AddAccount(account)=> if (!accountList.contains(account.userName)) {
        accountList += account.userName -> account
      }
      else {
        throw new Exception("Username matching")
      }
    }
  }

