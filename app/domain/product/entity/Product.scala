package domain.product.entity

case class Product(productId: ProductId, name: String, price: Int, productInfo: ProductInfo){
  def isPersisted = productId.isPersisted
}

object Product{
  def apply(name: String, price: Int, description: String) =
    new Product(ProductId.apply, name, price, ProductInfo(description))

  def apply(id: Int, name: String, price: Int, description: String) =
    new Product(ProductId.apply(id), name, price, ProductInfo(description))
}


