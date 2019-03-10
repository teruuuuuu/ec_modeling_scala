package core.domain.order.model

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
}
