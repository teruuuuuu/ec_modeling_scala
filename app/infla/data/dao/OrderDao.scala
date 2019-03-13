package infla.data.dao

import core.domain.order.model.Order
import play.api.db.slick.HasDatabaseConfigProvider
import slick.dbio.DBIOAction
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait OrderDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val Orders = TableQuery[OrderTable]

  case class OrderSchema(orderId: Option[Int], orderStatus: Int, userId: Int)

  protected class OrderTable(tag: Tag) extends Table[OrderSchema](tag, "ORDER_T") {
    def orderId = column[Int]("ORDER_ID", O.PrimaryKey, O.AutoInc)
    def orderStatus = column[Int]("ORDER_STATUS")
    def userId = column[Int]("USER_ID")
//    def * = (orderId, orderStatus, userId)
def * = (orderId.?, orderStatus, userId) <> (OrderSchema.tupled, OrderSchema.unapply)
  }

}

