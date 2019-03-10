package core.domain.order.model

case class Order(orderId: Option[Int], userId: Int, status: OrderStatus) {

}
