package com.hadilq.guidomia.guidomia.impl.data.mapper

import com.hadilq.guidomia.guidomia.impl.R
import com.hadilq.guidomia.guidomia.impl.data.entity.CarDataEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.*
import javax.inject.Inject

class CarDataMapper @Inject constructor() {

  fun map(data: CarDataEntity) = data.run {
    CarEntity(
      model = ModelEntity(model),
      make = MakeEntity(make),
      price = PriceEntity(marketPrice),
      rate = Rate.values()[rating - 1],
      pros = prosList,
      cons = consList,
      image = ImageEntity(
        when (model) {
          "Range Rover" -> R.drawable.range_rover
          "Roadster" -> R.drawable.alpine_roadster
          "3300i" -> R.drawable.bmw_330i
          "GLE coupe" -> R.drawable.mercedez_benz_glc
          else -> 0
        }
      )
    )
  }
}
