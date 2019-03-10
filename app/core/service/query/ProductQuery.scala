package core.service.query

import controllers.dto.ProductDto

import scala.concurrent.Future

trait ProductQuery {
  def findProductDtoByName(name: String): Future[Seq[ProductDto]]
}
