package infla.data.dao

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait CreditPayDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val CreditPays = TableQuery[CreditPayTable]

  case class CreditPaySchema(creditPayId: Option[Int], paymentId: Int)

  protected class CreditPayTable(tag: Tag) extends Table[CreditPaySchema](tag, "CREDIT_PAY") {
    def creditPayId = column[Int]("CREDIT_PAY_ID", O.PrimaryKey, O.AutoInc)
    def paymentId = column[Int]("PAYMENT_ID")
    def * = (creditPayId.?, paymentId) <> (CreditPaySchema.tupled, CreditPaySchema.unapply)
  }
}