package core.service

import core.domain.product.repository.ProductRepository
import core.service.dto.ProductDto
import javax.inject.Inject
import play.api.mvc.ControllerComponents

import scala.concurrent.Future

class ProductServiceImpl @Inject()(productRepository: ProductRepository, cc: ControllerComponents) extends ProductService {

  def findAllProduct = productRepository.findAll
  def findProductById(id: Int) = productRepository.findById(id)
}
