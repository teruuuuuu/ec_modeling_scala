package core.service

import core.domain.product.repository.ProductRepository
import controllers.dto.ProductDto
import core.service.query.ProductQuery
import javax.inject.Inject
import play.api.mvc.ControllerComponents

import scala.concurrent.Future

class ProductServiceImpl @Inject()(productRepository: ProductRepository, productQuery: ProductQuery, cc: ControllerComponents)
  extends ProductService {
  def findProductByName(name: String):Future[Seq[ProductDto]] = productQuery.findProductDtoByName(name)
}
