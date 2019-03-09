package controllers

import controllers.helper.ProductResponse
import core.service.ProductService
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext



@Singleton
class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with ProductResponse{


  def search() = Action.async { implicit request: Request[AnyContent] =>
    productService.findAllProduct.map(products =>
      Ok(Json.toJson(products))
    )
  }

  def searchById(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    productService.findProductById(id).map(p =>
      p match {
        case Some(x) => Ok(Json.toJson(x))
        case _ => Ok("") //見つからなかった時
      }
    )
  }
}
