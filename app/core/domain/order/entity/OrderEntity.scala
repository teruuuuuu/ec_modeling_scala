package core.domain.order.entity

import core.domain.order.model.{Item, Order, PaymentInfo}

case class OrderEntity(order: Order, items: List[Item], paymentInfo: Option[PaymentInfo]) {
  def price: Int = items.foldLeft(0)((acc, cur) => acc + cur.sum)
}
