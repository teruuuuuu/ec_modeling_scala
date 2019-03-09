package infla.session

import play.api.mvc.{AnyContent, Request, Session}

trait MySession {
  private val LOGIN_USER_ID = "login_user_id"

  def getLoginUserId()(implicit session: Session): Option[Int] = {
    session.get(LOGIN_USER_ID) match {
      case Some(x) if scala.util.Try(x.toInt).isSuccess => Some(x.toInt)
      case _ => None
    }
  }

  def setLoginUserId(userId: Int): (String, String) = {
    (LOGIN_USER_ID -> userId.toString)
  }
}