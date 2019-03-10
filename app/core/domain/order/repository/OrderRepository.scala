package core.domain.order.repository

import core.domain.order.entity.OrderEntity

import scala.concurrent.Future

trait OrderRepository {
  def save(orderEntity: OrderEntity): Future[OrderEntity]
}
