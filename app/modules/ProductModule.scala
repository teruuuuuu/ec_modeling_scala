package modules

import com.google.inject.AbstractModule
import repository.{ProductRepository, ProductRepositoryImpl}

class ProductModule extends AbstractModule {

  override def configure() = {
    bind(classOf[ProductRepository]).to(classOf[ProductRepositoryImpl])
  }
}
