package controllers

import controllers.form.ProductForm
import controllers.response.ProductResponse
import core.domain.user.repository.UserRepository
import core.service.ProductService
import infla.session.MySession
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class LoginController @Inject()(userRepository: UserRepository, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with MySession with ProductForm {

  def login() = Action.async { implicit request: Request[AnyContent] =>
    checkLoginForm match {
      case Some(form) => {
        userRepository.findByName(form.name).map {
          case Some(x) if x.password.equals(form.password)=> {
            Ok("login success").
              withSession(request.session + setLoginUserId(x.userId.get))
          }
          case _ => Ok("login fail")
        }
      }
      case _ => {
        Future.successful(Ok("login fail"))
      }
    }
  }

  def logout() = Action.async { implicit request: Request[AnyContent] =>
    Future.successful(Ok("logout").withNewSession)
  }

}
