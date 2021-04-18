package com.hadilq.guidomia.guidomia.impl.data.mapper

import com.hadilq.guidomia.database.api.CarDatabaseEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.*
import javax.inject.Inject

class CarDatabaseMapper @Inject constructor() {

  fun map(car: CarDatabaseEntity) = with(car) {
    CarEntity(
      make = MakeEntity(make),
      model = ModelEntity(model),
      image = ImageEntity(image),
      price = PriceEntity(price),
      rate = Rate.values()[rating - 1],
      pros = prosList,
      cons = consList,
    )
  }

  fun map(car: CarEntity) = with(car) {
    CarDatabaseEntity(
      make = make.value,
      model = model.value,
      image = image.value,
      price = price.value,
      rating = rate.value,
      prosList = pros,
      consList = cons,
    )
  }
}
