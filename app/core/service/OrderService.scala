package core.service

import scala.concurrent.Future

trait OrderService {
  def updateItem(userId: Int, productId: Int, number: Int): Future[Either[Int, Int]]
  def bankConfirm(userId: Int, bankAccount: String): Future[Either[Int, Int]]
  def creditConfirm(userId: Int): Future[Either[Int, Int]]
}
