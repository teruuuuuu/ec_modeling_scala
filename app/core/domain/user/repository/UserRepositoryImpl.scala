package core.domain.user.repository

import core.domain.user.entity.User
import infla.data.dao.{UserDao, UserInfoDao}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class UserRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends UserRepository with UserDao with UserInfoDao {
  import profile.api._

  override def save(user: User): Future[User] = {
    if(user.isPersisted) {
      db.run(for {
        _ <- Users.filter(_.userId === user.userId.get).update((user.userId.get, user.name, user.password))
        _ <- UserInfos.filter(_.userId === user.userId.get).update(user.userId.get, user.userInfo.address, user.userInfo.postalCode)
      } yield ()).map(x => {
        user
      })
    } else {
      db.run(for {
        u <- insertUser += (0, user.name, user.password)
        _ <- UserInfos += (u._1, user.userInfo.address, user.userInfo.postalCode)
      } yield u._1).map(id => {
        User.apply(Some(id), user.name, user.password, user.userInfo)
      })
    }
  }
}
