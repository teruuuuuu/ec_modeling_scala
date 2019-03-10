package infla.data.dao

import core.domain.order.model.Order
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait OrderDao extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  lazy val Orders = TableQuery[OrderTable]
  val insertOrder = (Orders returning Orders.map(_.orderId)) into ((order, orderId) => (orderId))
  val updateOrder = (order: (Int, Int, Int)) => (Orders insertOrUpdate(order._1, order._2, order._3))


  def save(input: Order): Future[(Int, Int, Int)] = db.run(saveIO(input))

  def saveIO(input: Order): DBIO[(Int, Int, Int)] = input.orderId.isEmpty match {
    case true => saveDBIO(0, input.userId, input.status.code)
    case false => updateDBIO(input.orderId.get, input.userId, input.status.code)
  }

  def saveDBIO(input: (Int, Int, Int)): DBIO[(Int, Int, Int)] = {
    (Orders returning Orders.map(_.orderId) into ((order, orderId) => (orderId, order._2, order._3))) += input
  }

  def updateDBIO(input: (Int, Int, Int)): DBIO[(Int, Int, Int)] = {
    (Orders insertOrUpdate input)
    Orders.filter(_.orderId === input._1).result.head
  }

  protected class OrderTable(tag: Tag) extends Table[(Int, Int, Int)](tag, "ORDER_T") {
    def orderId = column[Int]("ORDER_ID", O.PrimaryKey, O.AutoInc)
    def orderStatus = column[Int]("ORDER_STATUS")
    def userId = column[Int]("USER_ID")
    def * = (orderId, orderStatus, userId)
  }

}

