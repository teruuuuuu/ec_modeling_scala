package core.service.query

import controllers.dto.ProductDto
import infla.data.dao.{ProductDao, ProductInfoDao}
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

class ProductQueryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends ProductQuery with ProductDao with ProductInfoDao {
  import profile.api._

  override def findProductDtoByName(name: String): Future[Seq[ProductDto]] =
    db.run(
      Products.filter(_.name like ('%'+name+'%')).join(ProductInfos).on(_.productId === _.productId).result
    ).map(_.map{case (p, i) => ProductDto(p._1, p._2, p._3, i._2)})
}
