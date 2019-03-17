package core.service

import core.domain.order.repository.OrderRepository
import core.domain.product.repository.ProductRepository
import javax.inject.Inject

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Success

class OrderServiceImpl @Inject()(orderRepository: OrderRepository, productRepository: ProductRepository)(implicit ec: ExecutionContext)
  extends OrderService {

  def updateItem(userId: Int, productId: Int, number: Int): Future[Either[Int, Int]] = {
    (for{
      cart <- orderRepository.userCart(userId)
      product <- productRepository.find(productId)
    } yield (cart, product)) map {
      case (cart, Some(product)) => {
        Await.result(orderRepository.save(cart.updateItem(product, number)), Duration.Inf) match {
          case Right(x) => Right(1)
          case _ => Left(1)
        }
      }
      case _ => Left(1)
    }
  }

  def bankConfirm(userId: Int, bankAccount: String): Future[Either[Int, Int]] = {
    orderRepository.userCart(userId).map{
      case cart => {
        cart.bankPay(bankAccount) match {
          case Right(x) => {
            Await.result(orderRepository.save(x), Duration.Inf) match {
              case Right(j) => Right(1)
              case _ => Left(1)
            }
          }
          case _ => Left(1)
        }
      }
      case _ => Left(1)
    }
  }

  def creditConfirm(userId: Int): Future[Either[Int, Int]] = {
    orderRepository.userCart(userId).map{
      case cart => {
        cart.creditPay match {
          case Right(x) => {
            Await.result(orderRepository.save(x), Duration.Inf) match {
              case Right(j) => Right(1)
              case _ => Left(1)
            }
          }
          case _ => Left(1)
        }
      }
      case _ => Left(1)
    }
  }

}
