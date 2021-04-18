package com.hadilq.guidomia.guidomia.impl.domain.entity

object CarEntityProvider {

  fun provide(
    make: MakeEntity = MakeEntity("make"),
    model: ModelEntity = ModelEntity("model")
  ) = CarEntity(
    make = make,
    model = model,
    image = ImageEntity(0),
    price = PriceEntity(1.0f),
    rate = Rate.FIVE,
    pros = listOf("pro"),
    cons = listOf("con"),
  )
}
