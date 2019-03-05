package core.domain.product.repository

import core.domain.product.entity.{Product, ProductId, ProductInfo}
import core.service.dto.ProductDto
import infla.data.dao.{ProductDao, ProductInfoDao}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends ProductRepository with ProductDao with ProductInfoDao {
  import profile.api._

  private def toProductDto(p:(Int, String, Int), i: (Int, String)) = ProductDto(p._1, p._2, p._3, i._2)

  def save(product: Product) = {
    product.isPersisted match {
      case false => {
        db.run(for {
          p <- insertProduct += (0, product.name, product.price)
          _ <- ProductInfos += (p._1, product.productInfo.description)
        } yield ())
      }
      case true => {
        db.run(for {
          _ <- Products.filter { _.productId === product.productId.value }.map(p => (p.name, p.price)).update((product.name, product.price))
          _ <- ProductInfos.filter { _.productId === product.productId.value }.map(_.description).update(product.productInfo.description)
        } yield ())
      }
    }
  }

  def find(id: ProductId): Future[Option[Product]] = {
    db.run(
      for {
        p <- Products.filter(_.productId === id.value).result.headOption
        pi <- ProductInfos.filter(_.productId === id.value).result.headOption
      } yield (p, pi)
    ).map(ps => {
      ps match {
        case (Some(p), Some(pi)) => Some(Product.apply(ProductId.apply(p._1), p._2, p._3, ProductInfo.apply(pi._2)))
        case _ => None
      }
    })
  }

  def findAll =
    db.run(
      Products.join(ProductInfos).on(_.productId === _.productId).result
    ).map(ps => {
    ps.map(p => toProductDto(p._1, p._2))
  })


  def findById(id: Int) = {
    db.run(
      Products.join(ProductInfos).on(_.productId === _.productId).filter(_._1.productId === id).result.headOption
    ).map(p => {
      p match {
        case Some(x) => Some(toProductDto(x._1, x._2))
        case _ => None
      }
    })
  }
}