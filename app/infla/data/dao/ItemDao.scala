package infla.data.dao

import java.sql.Timestamp

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait ItemDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val Items = TableQuery[ItemTable]

  protected class ItemTable(tag: Tag) extends Table[(Int, Int, Int, Int, Timestamp)](tag, "ORDER_T") {
    def itemId = column[Int]("ITEM_ID", O.PrimaryKey, O.AutoInc)
    def orderId = column[Int]("ORDER_ID")
    def productId = column[Int]("PRODUCT_ID")
    def price = column[Int]("PRICE")
    def number = column[Int]("NUMBER")
    def updateDate = column[Timestamp]("UPDATE_DATE")
    def * = (itemId, productId, price, number, updateDate)
  }
}