package core.domain.user.entity

case class UserInfo(address: String, postalCode: String) {

}


object UserInfo {
  def apply(address: String, postalCode: String) = new UserInfo(address, postalCode)
}