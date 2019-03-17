package core.domain.order.repository

import java.sql.Timestamp

import core.domain.order.entity.OrderEntity
import core.domain.order.model._
import infla.data.dao._
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends OrderRepository with OrderDao with ItemDao with PaymentInfoDao with BankPayDao with CreditPayDao {
  import profile.api._

  override def userCart(userId: Int): Future[OrderEntity] = {
    db.run(for{
      order <- Orders.filter(o => o.userId === userId && o.orderStatus === OrderStatus.Shopping.code).result.headOption
      items <- Items.filter(_.orderId === order.map(_.orderId.get).getOrElse(-1)).result
      paymentInfo <- PaymentInfos.filter(_.orderId === order.map(_.orderId.get).getOrElse(-1)).result.headOption
      bank <- BankPays.filter(_.paymentId === paymentInfo.map(_.paymentId.get).getOrElse(-1)).result.headOption
      credit <- CreditPays.filter(_.paymentId === paymentInfo.map(_.paymentId.get).getOrElse(-1)).result.headOption
    } yield (order, items, paymentInfo, bank, credit)).map{ case (order, items, paymentInfo, bank, credit) => {
        order match {
          case None => OrderEntity(Order(None, userId, OrderStatus.Shopping), List(), None)
          case Some(x) => recordToEntity(order.get, items, paymentInfo, bank, credit)
        }
      }
    }
  }

  override def save(orderEntity: OrderEntity): Future[Either[Int, OrderEntity]] = {
    val order = orderEntity.order
    val items = orderEntity.items
    val paymentInfo = orderEntity.paymentInfo

    val query = for {
      order <- saveOrderQuery(OrderSchema(order.orderId, order.status.code, order.userId))
      item <- saveItem(order.orderId.get, items)
      payment <- savePaymentInfo(order.orderId.get, paymentInfo)
    } yield (order, item, payment)

    db.run(query transactionally).map(a => {
      Right(recordToEntity(a._1, a._2, a._3._1, a._3._2, a._3._3))
    })
  }

  private def recordToEntity(order: OrderSchema, items: Seq[ItemSchema], paymentInfo: Option[PaymentInfoSchema],
                             bankPay: Option[BankPaySchema], creditPays: Option[CreditPaySchema]): OrderEntity = {
    val o = Order(order.orderId, order.userId, OrderStatus(order.orderStatus))
    val i = items.map(k => Item(k.itemId, k.productId, k.price, k.number, k.updateDate.toLocalDateTime))
    val p = paymentInfo.map(p => {
      PaymentInfo(
        p.paymentId, PaymentType(p.paymentType), p.isPayed, p.price, p.dueDate.toLocalDateTime, p.paymentDate.map(_.toLocalDateTime),
        if(p.paymentType == PaymentType.Bank.code) {
          bankPay.map(b => BankPay(b.bankPayId, b.bankAccount)).get
        } else {
          creditPays.map(c => Credit(c.creditPayId)).get
        })
    })
    OrderEntity(o, i.toList, p)
  }

  private def saveOrderQuery(order: OrderSchema) = if(order.orderId.isEmpty) {
    (Orders returning Orders.map(_.orderId) into ((order, orderId) => order.copy(orderId = Some(orderId)))) += order
  } else {
    for {
      rowsAffected <- Orders.filter(_.orderId === order.orderId.get).map(a => (a.userId, a.orderStatus)).update(order.userId, order.orderStatus)
      result <- rowsAffected match {
        case _ => DBIO.successful(order)
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

  private def savePaymentInfo(orderId: Int, paymentInfo: Option[PaymentInfo]) = {
    def syncPamentInfo = (orderId: Int, paymentInfo: Option[PaymentInfoSchema]) => {
      paymentInfo match {
        case None => DBIO.successful(None)
        case Some(p) if p.paymentId.isEmpty =>
          (PaymentInfos returning PaymentInfos.map(_.paymentId)) into ((paymentInfo, paymentId) => Some(paymentInfo.copy(paymentId = Some(paymentId)))) += p
        case Some(p) if p.paymentId.isDefined => {
          for {
            rowsAffected <- PaymentInfos.filter(_.paymentId === p.paymentId).
              map(r => (r.orderId, r.isPayed, r.paymentType, r.price, r.dueDate, r.paymentDate)).
              update(orderId, p.isPayed, p.paymentType, p.price, p.dueDate, p.paymentDate)
            result <- rowsAffected match { case _ => DBIO.successful(Some(p))}
          } yield result
        }
      }
    }

    def syncBankPay = (orderId: Int, paymentInfo: Option[PaymentInfoSchema], bankPay: Option[PayDetail]) => {
      if(paymentInfo.isEmpty || bankPay.isEmpty || !bankPay.get.isInstanceOf[BankPay]) {
        DBIO.successful(None)
      } else {
        bankPay.get match {
          case x: BankPay if x.bankPayId.isEmpty => {
            val record = BankPaySchema(x.bankPayId, paymentInfo.get.paymentId.get, x.bankAcount)
            (BankPays returning BankPays.map(_.bankPayId)) into ((bankPay, bankPayId) => Some(bankPay.copy(bankPayId = Some(bankPayId)))) += record
          }
          case x: BankPay if x.bankPayId.isDefined => {
            val record = BankPaySchema(x.bankPayId, paymentInfo.get.paymentId.get, x.bankAcount)
            for {
              rowsAffected <- BankPays.filter(_.bankPayId === record.bankPayId.get).
                map(r => (r.paymentId, r.bankAccount)).
                update(record.paymentId, record.bankAccount)
              result <- rowsAffected match { case _ => DBIO.successful(Some(record))}
            } yield result
          }
          case _ =>  DBIO.successful(None)
        }
      }
    }

    def syncCreditPay = (orderId: Int, paymentInfo: Option[PaymentInfoSchema], bankPay: Option[PayDetail]) => {
      if(paymentInfo.isEmpty || bankPay.isEmpty || !bankPay.get.isInstanceOf[BankPay]) {
        DBIO.successful(None)
      } else {
        bankPay.get match {
          case x: Credit if x.creditId.isEmpty => {
            val record = CreditPaySchema(x.creditId, paymentInfo.get.paymentId.get)
            (CreditPays returning CreditPays.map(_.creditPayId)) into ((creditPay, creditPayId) => Some(creditPay.copy(creditPayId = Some(creditPayId)))) += record
          }
          case x: Credit if x.creditId.isDefined => {
            val record = CreditPaySchema(x.creditId, paymentInfo.get.paymentId.get)
            for {
              rowsAffected <- CreditPays.filter(_.creditPayId === record.creditPayId.get).
                map(_.paymentId).update(record.paymentId)
              result <- rowsAffected match { case _ => DBIO.successful(Some(record))}
            } yield result
          }
          case _ =>  DBIO.successful(None)
        }
      }
    }

    for {
      order <- syncPamentInfo(orderId, paymentInfo.map(p=> PaymentInfoSchema(None, orderId, p.isPayed, p.paymentType.code, p.price, Timestamp.valueOf(p.dueDate), p.paymentDate.map(Timestamp.valueOf(_)))))
      bank <- syncBankPay(orderId, order, paymentInfo.map(_.payDetail))
      credit <- syncCreditPay(orderId, order, paymentInfo.map(_.payDetail))
    } yield (order, bank, credit)
  }
}