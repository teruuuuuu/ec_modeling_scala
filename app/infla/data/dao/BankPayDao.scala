package infla.data.dao

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait BankPayDao extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._
  lazy val BankPays = TableQuery[BankPayTable]

  case class BankPaySchema(bankPayId: Option[Int], paymentId: Int, bankAccount: String)

  protected class BankPayTable(tag: Tag) extends Table[BankPaySchema](tag, "BANK_PAY") {
    def bankPayId = column[Int]("BANK_PAY_ID", O.PrimaryKey, O.AutoInc)
    def paymentId = column[Int]("PAYMENT_ID")
    def bankAccount = column[String]("BANK_ACCOUNT")
    def * = (bankPayId.?, paymentId, bankAccount) <> (BankPaySchema.tupled, BankPaySchema.unapply)
  }
}