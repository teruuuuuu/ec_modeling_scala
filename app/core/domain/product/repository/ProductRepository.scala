package core.domain.product.repository

import core.domain.product.entity.Product
import controllers.dto.ProductDto

import scala.concurrent.Future

trait ProductRepository {
  def save(product: Product): Future[Product]
  def find(id: Int): Future[Option[Product]]
  def findAll: Future[Seq[ProductDto]]
  def findById(id: Int): Future[Option[ProductDto]]
}
