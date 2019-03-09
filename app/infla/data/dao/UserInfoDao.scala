package infla.data.dao

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait UserInfoDao extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._
  lazy val UserInfos = TableQuery[UserInfoTable]

  protected class UserInfoTable(tag: Tag) extends Table[(Int, String, String)](tag, "USER_INFO") {
    def userId = column[Int]("USER_ID", O.PrimaryKey)
    def address = column[String]("ADDRESS")
    def postalCode = column[String]("POSTAL_CODE")
    def * = (userId, address, postalCode)
  }
}
