package controllers

import controllers.form.OrderForm
import core.domain.user.repository.UserRepository
import core.service.OrderService
import infla.session.MySession
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}


@Singleton
class OrderController @Inject()(userRepository: UserRepository, orderService: OrderService, cc: ControllerComponents)
                               (implicit executionContext: ExecutionContext) extends AbstractController(cc) with MySession with OrderForm {

  def updateItem() = Action.async { implicit request: Request[AnyContent] =>
    checkUpdateItemForm() match {
      case Some(form) => {
        getLoginUserId match {
          case Some(userId) => {
            orderService.updateItem(userId, form.productId, form.number) map {
              case Right(x) => Ok(x.toString).withSession(request.session)
              case _ => BadRequest(1.toString)
            }
          }
          case _ => Future.successful(BadRequest(1.toString))
        }
      }
      case _ => Future.successful(BadRequest(1.toString))
    }
  }

  def bankConfirm() = Action.async { implicit request: Request[AnyContent] =>
    checkBankPayForm() match {
      case Some(form) => {
        getLoginUserId match {
          case Some(userId) => {
            orderService.bankConfirm(userId, form.bankAccount) map {
              case Right(x) => Ok(1.toString).withSession(request.session)
              case _ => BadRequest(1.toString)
            }
          }
          case _ => Future.successful(BadRequest(1.toString))
        }
      }
      case _ => Future.successful(BadRequest(1.toString))
    }
  }

  def creditConfirm() = Action.async { implicit request: Request[AnyContent] =>
    getLoginUserId match {
      case Some(userId) => {
        orderService.creditConfirm(userId) map {
          case Right(x) => Ok(1.toString).withSession(request.session)
          case _ => BadRequest(1.toString)
        }
      }
      case _ => Future.successful(BadRequest(1.toString))
    }
  }
}
