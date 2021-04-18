package com.hadilq.guidomia.guidomia.impl.domain.entity

object MakeEntityProvider {
  fun provide(make: String = "make") = MakeEntity(make)
}
