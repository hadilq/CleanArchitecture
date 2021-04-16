package com.hadilq.guidomia.guidomia.impl.presentation

import com.hadilq.guidomia.guidomia.impl.domain.entity.MakeEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.ModelEntity

enum class CarListType(val index: Int) {
  CAR(0), FILTER(1)
}


sealed class CarListModel(
  val type: CarListType
)


data class CarModel(
  val model: ModelEntity,
  val make: MakeEntity,
) : CarListModel(CarListType.CAR)

data class FilterModel(
  val x:Int = 1,
) : CarListModel(CarListType.FILTER)
