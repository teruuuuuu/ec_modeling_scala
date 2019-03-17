package controllers.form

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AnyContent, Request}


trait OrderForm {
  case class UpdateItemForm(productId: Int, number: Int)

  implicit val updateItemForm = Form(
    mapping(
      "product_id" -> number,
      "number" -> number,
    )(UpdateItemForm.apply)(UpdateItemForm.unapply)
  )

  def checkUpdateItemForm ()(implicit request:Request[AnyContent]): Option[UpdateItemForm] = {
    updateItemForm.bindFromRequest.value
  }

  case class BankPayForm(bankAccount: String)

  implicit val bankPayForm = Form(
    mapping(
      "bank_account" -> nonEmptyText(minLength = 8),
    )(BankPayForm.apply)(BankPayForm.unapply)
  )

  def checkBankPayForm ()(implicit request:Request[AnyContent]): Option[BankPayForm] = {
    bankPayForm.bindFromRequest.value
  }
}
