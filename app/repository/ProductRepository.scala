package repository

import dto.ProductDto

import scala.concurrent.Future

trait ProductRepository {
  def findAll: Future[Seq[ProductDto]]
  def findById(id: Int): Future[Option[ProductDto]]
}
