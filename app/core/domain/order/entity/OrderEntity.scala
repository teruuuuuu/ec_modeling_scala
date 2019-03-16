package core.domain.order.entity

import java.time.LocalDateTime

import core.domain.order.model.{PayDetail, _}
import core.domain.product.model.Product

case class OrderEntity(var order: Order, var items: List[Item], var paymentInfo: Option[PaymentInfo]) {
  def updateItem(product: Product, number: Int): OrderEntity = {
    this.items = items.zipWithIndex.find(_._1.productId == product.productId.get).map(_._2) match {
      case Some(x) => items.slice(0, x) ++ items.slice(x + 1, items.length) :+
        items(x).copy(price = product.price, number = number, updateDate = LocalDateTime.now())
      case _ => items :+ Item(None, product.productId.get, product.price, number, LocalDateTime.now)
    }
    this
  }

  def bankPay(bankAccount: String): Either[Int, OrderEntity] = {
    confirm(PaymentType.Bank, BankPay(None, bankAccount))
  }

  def creditPay(): Either[Int, OrderEntity] =  {
    confirm(PaymentType.Credit, Credit(None))
  }

  private def confirm(payType: PaymentType, payDetail: PayDetail): Either[Int, OrderEntity] = {
    order.status match {
      case x if x == OrderStatus.Shopping && items.filter(_.number > 0).length > 0 => {
        this.order = this.order.copy(status = OrderStatus.Confirm)
        this.paymentInfo = Some(PaymentInfo(None, payType, 0, price, LocalDateTime.now.plusDays(7), None, payDetail))
        Right(this)
      }
      case _ => Left(1)
    }
  }

  private def price: Int = items.foldLeft(0)((acc, cur) => acc + cur.sum)
}
