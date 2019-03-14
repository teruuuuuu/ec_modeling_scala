package task

import java.time.LocalDateTime

import com.google.inject.Inject
import core.domain.order.entity.OrderEntity
import core.domain.order.model._
import core.domain.order.repository.OrderRepository
import core.domain.product.model.Product
import core.domain.product.repository.ProductRepository
import core.domain.user.model.{User, UserInfo}
import core.domain.user.repository.UserRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DataInit @Inject()(userRepository: UserRepository, productRepository: ProductRepository, orderRepository: OrderRepository) {
  def userDataInit = {
    Await.result(userRepository.save(User.apply(None, "user1", "password", UserInfo.apply("address1", "postal1"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user2", "password", UserInfo.apply("address2", "postal2"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user3", "password", UserInfo.apply("address3", "postal3"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user4", "password", UserInfo.apply("address4", "postal4"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user5", "password", UserInfo.apply("address5", "postal5"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user6", "password", UserInfo.apply("address6", "postal6"))), Duration.Inf)
    Await.result(userRepository.save(User.apply(None, "user7", "password", UserInfo.apply("address7", "postal7"))), Duration.Inf)
  }


  def productDataInit = {
    Await.result(productRepository.save(Product.apply(None, "product1", 100, "product1 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product2", 200, "product2 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product3", 300, "product3 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product4", 400, "product4 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product5", 500, "product5 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product6", 600, "product6 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply(None, "product7", 700, "product7 description")), Duration.Inf)
  }

  def orderDataInit = {
    val user1 = Await.result(userRepository.findByName("user1"), Duration.Inf).get
    val result1 = Await.result(orderRepository.save(OrderEntity.apply(Order.apply(Some(5), user1.userId.get, OrderStatus.Shopping),
      List(Item(None, 1, 1, 1, 1, LocalDateTime.now), Item(None, 1, 2, 2, 2, LocalDateTime.now), Item(None, 1, 3, 3, 3, LocalDateTime.now)),
      Some(PaymentInfo(None, PaymentType.Bank, 0, 3000, LocalDateTime.now, None, BankPay(None, "aaa"))))), Duration.Inf)
    Await.result(orderRepository.save(OrderEntity.apply(Order.apply(Some(5), user1.userId.get, OrderStatus.Shopping),
      List(Item(Some(1), 1, 1, 1, 10, LocalDateTime.now), Item(Some(2), 1, 2, 2, 0, LocalDateTime.now), Item(None, 1, 3, 3, 3, LocalDateTime.now)),
      None)), Duration.Inf)
  }

  println("init task start")
  userDataInit
  productDataInit
  orderDataInit
  println("init task end")
}