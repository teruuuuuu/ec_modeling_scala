package core.domain.order.model

import java.time.LocalDateTime

case class Item(itemId: Option[Int], productId: Int, price: Int, number: Int, updateDate: LocalDateTime) {
  def sum() = price * number
}
