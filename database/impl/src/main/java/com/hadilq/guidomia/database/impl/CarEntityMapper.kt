package com.hadilq.guidomia.database.impl

import com.hadilq.guidomia.database.api.CarDatabaseEntity
import com.hadilq.guidomia.database.impl.entiry.CarEntity
import com.hadilq.guidomia.database.impl.entiry.CarWithProsCons
import com.hadilq.guidomia.database.impl.entiry.ProConEntity
import javax.inject.Inject

class CarEntityMapper @Inject constructor() {

  fun map(car: CarWithProsCons) = car.run {
    CarDatabaseEntity(
      make = this.car.make,
      model = this.car.model,
      image = this.car.image,
      price = this.car.price,
      rating = this.car.rating,
      prosList = proConList.filter { it.pro }.map { it.value },
      consList = proConList.filter { !it.pro }.map { it.value },
    )
  }

  fun mapToCarEntity(car: CarDatabaseEntity) = car.run {
    CarEntity(
      make = make,
      model = model,
      image = image,
      price = price,
      rating = rating,
    )
  }

  fun mapToProConEntity(
    carId: Long,
    pro: Boolean,
    value: String,
  ) = ProConEntity(
    carId = carId,
    pro = pro,
    value = value,
  )
}
