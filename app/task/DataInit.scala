package task

import com.google.inject.Inject
import core.domain.product.entity.Product
import core.domain.product.repository.ProductRepository

class DataInit @Inject()(productRepository: ProductRepository) {

  def productDataInit = {
    productRepository.save(Product.apply("product1", 100, "product1 description"))
    productRepository.save(Product.apply("product2", 200, "product2 description"))
    productRepository.save(Product.apply("product3", 300, "product3 description"))
    productRepository.save(Product.apply("product4", 400, "product4 description"))
    productRepository.save(Product.apply("product5", 500, "product5 description"))
    productRepository.save(Product.apply("product6", 600, "product6 description"))
    productRepository.save(Product.apply("product7", 700, "product7 description"))
  }

  println("init task start")
  productDataInit
  println("init task end")
}