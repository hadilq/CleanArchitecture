package com.hadilq.guidomia.guidomia.impl.data.mapper

import com.hadilq.guidomia.guidomia.impl.data.entity.CarDataEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.MakeEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.ModelEntity
import javax.inject.Inject

class CarDataMapper @Inject constructor() {

  fun map(data: CarDataEntity) = CarEntity(
    model = ModelEntity(data.model),
    make = MakeEntity(data.make)
  )
}
