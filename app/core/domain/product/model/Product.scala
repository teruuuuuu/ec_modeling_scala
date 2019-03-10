package core.domain.product.model

case class Product(productId: Option[Int], name: String, price: Int, productInfo: ProductInfo){
  def isPersisted = productId.isDefined
}

object Product{
  def apply(id: Option[Int], name: String, price: Int, description: String) =
    new Product(id, name, price, ProductInfo(description))
}


