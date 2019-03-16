package task

import com.google.inject.Inject
import core.domain.order.repository.OrderRepository
import core.domain.product.model.Product
import core.domain.product.repository.ProductRepository
import core.domain.user.model.{User, UserInfo}
import core.domain.user.repository.UserRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

class DataInit @Inject()(userRepository: UserRepository, productRepository: ProductRepository, orderRepository: OrderRepository)(implicit ec: ExecutionContext) {
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
    val user2 = Await.result(userRepository.findByName("user2"), Duration.Inf).get
    val product1 = Await.result(productRepository.findByName("product1"), Duration.Inf).headOption.get
    val product2 = Await.result(productRepository.findByName("product2"), Duration.Inf).headOption.get
    val product3 = Await.result(productRepository.findByName("product3"), Duration.Inf).headOption.get
    val product4 = Await.result(productRepository.findByName("product4"), Duration.Inf).headOption.get
    val product5 = Await.result(productRepository.findByName("product5"), Duration.Inf).headOption.get

    orderRepository.userCart(user1.userId.get).map{cart => {
      List((product1, 2), (product2, 3), (product1, 1), (product1, 5)).foldLeft(cart)((cart, cur) => {
        cart.updateItem(cur._1, cur._2)
      }).bankPay("12345") match {
        case Right(x) => orderRepository.save(x)
        case _ => println("update fail")
      }
    }}
  }

  println("init task start")
  userDataInit
  productDataInit
  orderDataInit
  println("init task end")
}