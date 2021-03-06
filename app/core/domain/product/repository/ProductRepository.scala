package core.domain.product.repository

import core.domain.product.model.Product
import controllers.dto.ProductDto

import scala.concurrent.Future

trait ProductRepository {
  def save(product: Product): Future[Product]
  def find(id: Int): Future[Option[Product]]
  def findByName(name: String): Future[Seq[Product]]
}
