package infla.modules

import com.google.inject.AbstractModule
import core.domain.order.repository.{OrderRepository, OrderRepositoryImpl}
import core.domain.product.repository.{ProductRepository, ProductRepositoryImpl}
import core.domain.user.repository.{UserRepository, UserRepositoryImpl}
import core.service.query.{ProductQuery, ProductQueryImpl}
import core.service.{OrderService, OrderServiceImpl, ProductService, ProductServiceImpl}

class DiModules extends AbstractModule {

  override def configure() = {
    bind(classOf[ProductRepository]).to(classOf[ProductRepositoryImpl])
    bind(classOf[ProductQuery]).to(classOf[ProductQueryImpl])
    bind(classOf[ProductService]).to(classOf[ProductServiceImpl])

    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])

    bind(classOf[OrderRepository]).to(classOf[OrderRepositoryImpl])
    bind(classOf[OrderService]).to(classOf[OrderServiceImpl])
  }
}

