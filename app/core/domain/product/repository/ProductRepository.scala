package core.domain.product.repository

import core.domain.product.entity.{Product, ProductId}
import core.service.dto.ProductDto

import scala.concurrent.Future

trait ProductRepository {
  def save(product: Product)
  def find(id: ProductId): Future[Option[Product]]
  def findAll: Future[Seq[ProductDto]]
  def findById(id: Int): Future[Option[ProductDto]]
}
