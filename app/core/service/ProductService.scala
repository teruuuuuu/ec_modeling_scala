package core.service

import controllers.dto.ProductDto

import scala.concurrent.Future

trait ProductService {
  def findAllProduct: Future[Seq[ProductDto]]
  def findProductById(id: Int): Future[Option[ProductDto]]
}
