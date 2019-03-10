package core.service

import controllers.dto.ProductDto

import scala.concurrent.Future

trait ProductService {
  def findProductByName(name: String): Future[Seq[ProductDto]]
}
