package com.hadilq.guidomia.guidomia.impl.presentation

import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.FilterEntity
import javax.inject.Inject

class CarModelMapper @Inject constructor() {

  fun map(carEntity: CarEntity, collapsed: Boolean = true) = carEntity.run {
    CarModel(
      model = model,
      make = make,
      image = image,
      price = price,
      rate = rate,
      pros = pros,
      cons = cons,
      collapsed = collapsed
    )
  }

  fun map(filterModel: FilterModel) = filterModel.run {
    FilterEntity(
      make = make,
      model = model,
    )
  }
}
