package dao

//import model.Product
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait ProductDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val Products = TableQuery[ProductsTable]

  val insertProduct = Products returning Products.map(_.productId) into ((product, productId) => (productId, product._2, product._3))

  protected class ProductsTable(tag: Tag) extends Table[(Int, String, Int)](tag, "PRODUCT") {
    def productId = column[Int]("PRODUCT_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def price = column[Int]("PRICE")
    def * = (productId, name, price)
  }
}
