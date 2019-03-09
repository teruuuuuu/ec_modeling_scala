package controllers

import controllers.helper.ProductResponse
import core.service.ProductService
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class LoginController @Inject()(productService: ProductService, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with ProductResponse{


  def login() = Action.async { implicit request: Request[AnyContent] =>
    Future.successful(Ok("login"))
  }

  def logout() = Action.async { implicit request: Request[AnyContent] =>
    Future.successful(Ok("logout").withNewSession)
  }

}
