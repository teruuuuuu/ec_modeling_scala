package infla.filter

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class MyFilter @Inject()(cc: ControllerComponents)(implicit val mat: Materializer, executionContext: ExecutionContext)
  extends AbstractController(cc) with Filter {
  val nochecks = List("/login", "/logout")

  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {
    if(!nochecks.find(_.equals(requestHeader.uri)).isDefined && !requestHeader.session.get("is_login").isDefined){
      Future.successful(Ok("no login").withSession(requestHeader.session + ("abc" -> "def")))
    } else{
      nextFilter(requestHeader).map { result =>
        result
      }
    }
  }

}