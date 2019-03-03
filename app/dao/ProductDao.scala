package dao

//import model.Product
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait ProductDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val Products = TableQuery[ProductsTable]

//  protected def toProduct(p: (Int, String, Int)) = Product(p._1, p._2, p._3)
  class ProductsTable(tag: Tag) extends Table[(Int, String, Int)](tag, "PRODUCT") {
    def productId = column[Int]("PRODUCT_ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def price = column[Int]("PRICE")
    def * = (productId, name, price)
  }
}
