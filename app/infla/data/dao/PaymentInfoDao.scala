package infla.data.dao

import java.sql.Timestamp
import java.time.LocalDateTime

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait PaymentInfoDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val PaymentInfos = TableQuery[PaymentInfoTable]

  case class PaymentInfoSchema(paymentId: Option[Int], orderId: Int, isPayed: Int, paymentType: Int, price: Int,
                               dueDate: Timestamp, paymentDate: Option[Timestamp])

  protected class PaymentInfoTable(tag: Tag) extends Table[PaymentInfoSchema](tag, "ORDER_T") {
    def paymentId = column[Int]("PAYMENT_ID", O.PrimaryKey, O.AutoInc)
    def orderId = column[Int]("ORDER_ID")
    def isPayed = column[Int]("IS_PAYED")
    def paymentType = column[Int]("PAYMENT_TYPE")
    def price = column[Int]("PRICE")
    def dueDate = column[Timestamp]("DUE_DATE")
    def paymentDate = column[Option[Timestamp]]("PAYMENT_DATE")
    def * = (paymentId.?, orderId, isPayed, paymentType, price, dueDate, paymentDate) <>
      (PaymentInfoSchema.tupled, PaymentInfoSchema.unapply)
  }
}
