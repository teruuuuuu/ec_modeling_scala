package infla.modules

import com.google.inject.AbstractModule
import core.domain.product.repository.{ProductRepository, ProductRepositoryImpl}
import core.service.{ProductService, ProductServiceImpl}

class ProductModule extends AbstractModule {

  override def configure() = {
    bind(classOf[ProductRepository]).to(classOf[ProductRepositoryImpl])
    bind(classOf[ProductService]).to(classOf[ProductServiceImpl])
  }
}
