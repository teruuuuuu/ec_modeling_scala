package infla.data.dao

import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile

trait UserDao extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._
  lazy val Users = TableQuery[UserTable]

  val insertUser = Users returning Users.map(_.userId) into ((user, userId) => (userId, user._2, user._3))

  protected class UserTable(tag: Tag) extends Table[(Int, String, String)](tag, "USER") {
    def userId = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("NAME")
    def password = column[String]("PASSWORD")
    def * = (userId, name, password)
  }
}
