package core.domain.product.entity

import core.util.Identifier

case class ProductId(value: Int)
  extends Identifier[Int] {
  override def isPersisted: Boolean = value != -1
}

object ProductId{
  def apply() = new ProductId(-1)
  def apply(value: Int) = new ProductId(value)
}