package core.domain.order.model

sealed abstract class PaymentType(value: Int) {
  def code = value
}

object PaymentType {
  case object None extends PaymentType(-1){
    override def toString: String = "該当なし"
  }
  case object Credit extends PaymentType(1) {
    override def toString: String = "クレジット"
  }
  case object Bank extends PaymentType(2) {
    override def toString: String = "銀行振込"
  }

  def apply(code: Int) = {code match {
    case x if x == Credit.code => Credit
    case x if x == Bank.code => Bank
    case _ => None
  }}
}