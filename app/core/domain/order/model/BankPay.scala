package core.domain.order.model

case class BankPay(bankPayId: Option[Int], bankAcount: String) extends PayDetail {}