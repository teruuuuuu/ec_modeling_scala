package controllers.form

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AnyContent, Request}


trait LoginForm {
  case class LoginForm(name: String, password: String)

  implicit val loginForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText,
    )(LoginForm.apply)(LoginForm.unapply)
  )

  def checkLoginForm ()(implicit request:Request[AnyContent]): Option[LoginForm] = {
    loginForm.bindFromRequest.value
  }
}
