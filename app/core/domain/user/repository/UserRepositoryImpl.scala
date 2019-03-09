package core.domain.user.repository

import core.domain.user.entity.{User, UserInfo}
import infla.data.dao.{UserDao, UserInfoDao}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class UserRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends UserRepository with UserDao with UserInfoDao {

  import profile.api._

  override def findByName(name: String): Future[Option[User]] = {
    db.run(for {
      u <- Users.filter(_.name === name).result
      ui <- UserInfos.filter( _.userId === u.headOption.map(_._1))result
    } yield (u.headOption, ui.headOption)
    ) map {
      case (Some(u), Some(ui)) => Some(User.apply(Some(u._1), u._2, u._3, UserInfo.apply(ui._2, ui._3)))
      case _ => None
    }
  }

  override def save(user: User): Future[User] = {
    if (user.isPersisted) {
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
