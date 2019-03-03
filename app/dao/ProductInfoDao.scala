package dao

//import model.ProductInfo
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait ProductInfoDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val ProductInfos = TableQuery[ProductInfoTable]

//  protected def toProductInfo(p: (Int, String)) = ProductInfo(p._1, p._2)
  class ProductInfoTable(tag: Tag) extends Table[(Int, String)](tag, "PRODUCT_INFO") {
    def productId = column[Int]("PRODUCT_ID", O.PrimaryKey)
    def description = column[String]("DESCRIPTION")
    def * = (productId, description)
//    def * = (productId, description) <> (ProductInfo.tupled, ProductInfo.unapply)
  }
}

