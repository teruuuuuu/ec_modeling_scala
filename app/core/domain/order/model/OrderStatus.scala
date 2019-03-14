package core.domain.order.model

import core.domain.order.model.OrderStatus.{Cancel, Confirm, None, Shopping}

sealed abstract class OrderStatus(value: Int) {
  def code = value
}
object OrderStatus {
  case object None extends OrderStatus(-1){
    override def toString: String = "該当なし"
  }
  case object Shopping extends OrderStatus(1){
    override def toString: String = "ショッピング"
  }
  case object Confirm extends OrderStatus(2){
    override def toString: String = "確定"
  }
  case object Cancel extends OrderStatus (100){
    override def toString: String = "キャンセル"
  }


  def apply(code: Int) = code match {
    case x if x == Shopping.code => Shopping
    case x if x == Confirm.code => Confirm
    case x if x == Cancel.code => Cancel
    case _ => None
  }
}