package dao

import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider

class ProductInfoDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends ProductInfoDao {
}