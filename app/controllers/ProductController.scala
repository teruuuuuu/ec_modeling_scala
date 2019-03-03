package controllers

import dto.ProductDto
import helper.ProductResponse
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import repository.ProductRepository

import scala.concurrent.ExecutionContext



@Singleton
class ProductController @Inject()(productRepository: ProductRepository, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with ProductResponse{


  def search() = Action.async { implicit request: Request[AnyContent] =>
    productRepository.findAll.map(products =>
      Ok(Json.toJson(products))
    )
  }

  def searchById(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    productRepository.findById(id).map(p =>
      p match {
        case Some(x) => Ok(Json.toJson(x))
        case _ => Ok("") //見つからなかった時
      }
    )
  }
}
