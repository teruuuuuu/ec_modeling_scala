package core.domain.order.model

import java.time.LocalDateTime

case class PaymentInfo(paymentId: Option[Int], paymentType: PaymentType, isPayed: Int, price: Int,
                       dueDate: LocalDateTime, paymentDate: Option[LocalDateTime], payDetail: PayDetail) {

}