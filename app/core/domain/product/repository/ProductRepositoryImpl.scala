package core.domain.product.repository

import core.domain.product.model.{Product, ProductInfo}
import controllers.dto.ProductDto
import infla.data.dao.{ProductDao, ProductInfoDao}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends ProductRepository with ProductDao with ProductInfoDao {
  import profile.api._

  private def toProductDto(p: (Int, String, Int), i: (Int, String)) = ProductDto(p._1, p._2, p._3, i._2)

  def save(product: Product): Future[Product] = {
    if(product.isPersisted) {
      db.run(for {
        p <- Products.filter(_.productId === product.productId.get).update((product.productId.get, product.name, product.price))
        pi <- ProductInfos.filter(_.productId === product.productId.get).update(product.productId.get, product.productInfo.description)
      } yield ()).map(x => {
        product
      })
    } else {
      db.run(for {
        p <- insertProduct += (0, product.name, product.price)
        _ <- ProductInfos += (p._1, product.productInfo.description)
      } yield p._1).map(id => {
        Product.apply(product.productId, product.name, product.price, product.productInfo)
      })
    }
  }

  def find(id: Int): Future[Option[Product]] = {
    db.run(
      for {
        p <- Products.filter(_.productId === id.value).result.headOption
        pi <- ProductInfos.filter(_.productId === id.value).result.headOption
      } yield (p, pi)
    ).map {
      case (Some(p), Some(pi)) => Some(Product.apply(Some(p._1), p._2, p._3, ProductInfo.apply(pi._2)))
      case _ => None
    }
  }
}
