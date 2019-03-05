package infla.modules

import play.api.inject.{SimpleModule, bind}
import task.DataInit

class DataInitModule  extends SimpleModule(bind[DataInit].toSelf.eagerly())