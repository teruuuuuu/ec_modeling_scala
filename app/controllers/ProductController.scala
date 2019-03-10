package controllers

import controllers.response.ProductResponse
import core.service.ProductService
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext


@Singleton
class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with ProductResponse {


  def searchByName(name: Option[String]) = Action.async { implicit request: Request[AnyContent] =>
    productService.findProductByName(name.getOrElse("")).map(p => Ok(Json.toJson(p)))
  }

  //  def searchById(id: Int) = Action.async { implicit request: Request[AnyContent] =>
  //    productService.findProductById(id).map(p =>
  //      p match {
  //        case Some(x) => Ok(Json.toJson(x))
  //        case _ => Ok("")
  //      }
  //    )
  //  }
}
