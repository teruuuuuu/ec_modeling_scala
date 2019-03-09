package infla.filter

import akka.stream.Materializer
import com.google.inject.Inject
import infla.session.MySession
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class MyFilter @Inject()(cc: ControllerComponents)(implicit val mat: Materializer, executionContext: ExecutionContext)
  extends AbstractController(cc) with Filter with MySession{
  val nochecks = List("/login", "/logout")

  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {
    implicit val session = requestHeader.session

    if(!nochecks.find(_.equals(requestHeader.uri)).isDefined && !getLoginUserId().isDefined){
      Future.successful(BadRequest("no login"))
    } else{
      nextFilter(requestHeader).map { result =>
        result
      }
    }

  }

}