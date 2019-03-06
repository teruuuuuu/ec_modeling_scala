package task

import com.google.inject.Inject
import core.domain.product.entity.Product
import core.domain.product.repository.ProductRepository

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class DataInit @Inject()(productRepository: ProductRepository) {

  def productDataInit = {
    Await.result(productRepository.save(Product.apply("product1", 100, "product1 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product2", 200, "product2 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product3", 300, "product3 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product4", 400, "product4 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product5", 500, "product5 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product6", 600, "product6 description")), Duration.Inf)
    Await.result(productRepository.save(Product.apply("product7", 700, "product7 description")), Duration.Inf)
  }

  println("init task start")
  productDataInit
  println("init task end")
}