package core.domain.order.repository

import core.domain.order.entity.OrderEntity

import scala.concurrent.Future

trait OrderRepository {
  def userCart(userId: Int): Future[OrderEntity]
  def save(orderEntity: OrderEntity): Future[OrderEntity]
}
