package core.domain.order.repository

import java.sql.Timestamp

import core.domain.order.entity.OrderEntity
import core.domain.order.model.{Item, Order}
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

    db.run(for {
      order <- saveOrderQuery(order)
      item <- saveItem(order._1, items)
    } yield (order, item)).foreach(a => {
      println(a._1)
      println(a._2)
    })


    Future.successful(null)
  }

  private def saveOrderQuery(order: Order) = if(order.orderId.isEmpty) {
    (Orders returning Orders.map(_.orderId) into ((order, orderId) => (orderId, order._2, order._3))) += (0, order.userId, order.status.code)
  } else {
    for {
      rowsAffected <- Orders.filter(_.orderId === order.orderId.get).map(a => (a.userId, a.orderStatus)).update(order.userId, order.status.code)
      result <- rowsAffected match {
        case _ => DBIO.successful(order.orderId.get, order.userId, order.status.code)
      }
    } yield result
  }

  private def saveItem(orderId: Int, items: List[Item]) = {
    val insertQuery = (item: ItemSchema) =>
      (Items returning Items.map(_.itemId) into ((item, itemId) => item.copy(itemId = Some(itemId)))) += item

    val updateQuery = (item: ItemSchema) => for {
      rowsAffected <- Items.filter(_.itemId === item.itemId.get).map(a => (a.orderId, a.productId, a.price, a.number, a.updateDate)).
        update(item.orderId, item.productId, item.price, item.number, item.updateDate)
      result <- rowsAffected match { case _ => DBIO.successful(item)}
    } yield result

    val deleteQuery = (item: ItemSchema) => for {
      rowsAffected <- Items.filter(_.itemId === item.itemId.get).delete
      result <- rowsAffected match { case _ => DBIO.successful(item)}
    } yield result

    DBIO.sequence(items.filter(i => !(i.itemId.isEmpty && i.number == 0)).
      map(i => ItemSchema(i.itemId, orderId, i.productId, i.price, i.number, Timestamp.valueOf(i.updateDate))).map(i => {
      if(i.itemId.isEmpty) {
        insertQuery(i)
      } else if(i.number > 0) {
        updateQuery(i)
      } else {
        deleteQuery(i)
      }
    }))
  }
}
