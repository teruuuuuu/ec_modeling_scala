package controllers.helper

import core.service.dto.ProductDto
import play.api.libs.json.{Json, Writes}

trait ProductResponse {
  implicit val ProductDtoWrite = new Writes[ProductDto] {
    def writes(productDto: ProductDto) = {
      Json.obj(
        "product_id" -> productDto.productId,
        "name" -> productDto.name,
        "price" -> productDto.price,
        "description" -> productDto.description
      )
    }
  }
}
