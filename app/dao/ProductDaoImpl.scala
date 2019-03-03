package dao

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

class ProductDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends ProductDao {
}