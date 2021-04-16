package com.hadilq.guidomia.guidomia.impl.presentation

import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import javax.inject.Inject

class CarModelMapper @Inject constructor() {

  fun map(carEntity: CarEntity) = carEntity.run {
    CarModel(
      model = model,
      make = make,
    )
  }
}
