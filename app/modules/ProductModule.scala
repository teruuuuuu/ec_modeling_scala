package modules

import com.google.inject.AbstractModule
import dao.{ProductDao, ProductDaoImpl, ProductInfoDao, ProductInfoDaoImpl}
import repository.{ProductRepository, ProductRepositoryImpl}

class ProductModule extends AbstractModule {

  override def configure() = {
//    bind(classOf[ProductDao]).to(classOf[ProductDaoImpl])
//    bind(classOf[ProductInfoDao]).to(classOf[ProductInfoDaoImpl])
    bind(classOf[ProductRepository]).to(classOf[ProductRepositoryImpl])
  }
}
