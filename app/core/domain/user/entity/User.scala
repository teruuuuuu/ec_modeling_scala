package core.domain.user.entity

case class User(userId: Option[Int], name: String, password: String, userInfo: UserInfo) {
  def isPersisted = userId.isDefined

}

object User{
  def apply(id: Option[Int], name: String, password: String, address: String, postalCode: String) = new UserInfo(address, postalCode)
}

