package core.domain.user.repository

import core.domain.user.model.User

import scala.concurrent.Future

trait UserRepository {
  def findByName(name: String): Future[Option[User]]
  def findById(id: Int): Future[Option[User]]
  def save(user: User): Future[User]
}
