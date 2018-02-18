package edu.knoldus.operation

import akka.actor.{Actor, ActorLogging, Props}
import edu.knoldus.util.Bill
import scala.collection.mutable.Map

object BillLinkAccount {
  def props(billList: Map[String, AnyRef]): Props = Props(new BillLinkAccount(billList))
  case class Link(accountNumber: String, bill: Bill)
}

class BillLinkAccount(billList: Map[String, AnyRef]) extends Actor with ActorLogging {

  import BillLinkAccount._

  override def preStart(): Unit = log.info(s"Account Bill Linker actor  started")
  override def postStop(): Unit = log.info(s"Account Bill Linker actor  stopped")

  override def receive : Receive = {
    case Link(accountNumber, bill) => if (billList.contains(accountNumber)) {
      billList(accountNumber) = List(billList(accountNumber), bill)
    }
    else {
      billList(accountNumber) = bill
    }
  }
}

