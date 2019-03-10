package core.domain.order.repository

import core.domain.order.entity.OrderEntity
import core.domain.product.repository.ProductRepository
import infla.data.dao._
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends OrderRepository with OrderDao with ItemDao with PaymentInfoDao {
  import profile.api._

  override def save(orderEntity: OrderEntity): Future[OrderEntity] = {
    val order = orderEntity.order
    val items = orderEntity.items
    val paymentInfo = orderEntity.paymentInfo


    db.run(for{
      o <- saveIO(order)
      _ <- Items.filter(_.orderId === o._1).delete
    } yield o)


    Future.successful(null)
  }
}
