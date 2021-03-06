package infla.data.dao

//import model.ProductInfo
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait ProductInfoDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val ProductInfos = TableQuery[ProductInfoTable]

  val insertProductInfo = ProductInfos returning ProductInfos.map(_.productId) into ((product, productId) => (productId, product._2))

  protected class ProductInfoTable(tag: Tag) extends Table[(Int, String)](tag, "PRODUCT_INFO") {
    def productId = column[Int]("PRODUCT_ID", O.PrimaryKey)
    def description = column[String]("DESCRIPTION")
    def * = (productId, description)
  }
}

