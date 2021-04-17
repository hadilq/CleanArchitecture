package com.hadilq.guidomia.guidomia.impl.presentation

import com.hadilq.guidomia.guidomia.impl.domain.entity.*

enum class CarListType(val index: Int) {
  LINE(0), CAR(1), FILTER(2)
}

sealed class CarListModel(
  val type: CarListType
)

object LineModel : CarListModel(CarListType.LINE)

data class CarModel(
  val model: ModelEntity,
  val make: MakeEntity,
  val image: ImageEntity,
  val price: PriceEntity,
  val rate: Rate,
  val pros: List<String>,
  val cons: List<String>,
  val collapsed: Boolean = true,
) : CarListModel(CarListType.CAR)

data class FilterModel(
  val x: Int = 1,
) : CarListModel(CarListType.FILTER)
