package task

import com.google.inject.Inject
import core.domain.product.entity.Product
import core.domain.product.repository.ProductRepository
import core.domain.user.entity.{User, UserInfo}
import core.domain.user.repository.UserRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DataInit @Inject()(userRepository: UserRepository, productRepository: ProductRepository) {
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

  println("init task start")
  userDataInit
  productDataInit
  println("init task end")
}