package core.service

import core.domain.order.repository.OrderRepository
import javax.inject.Inject
import play.api.mvc.ControllerComponents

class OrderServiceImpl @Inject()(orderRepository: OrderRepository, cc: ControllerComponents)
  extends OrderService {

}
