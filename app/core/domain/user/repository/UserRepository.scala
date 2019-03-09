package core.domain.user.repository

import core.domain.user.entity.User

import scala.concurrent.Future

trait UserRepository {
  def save(user: User): Future[User]
}
