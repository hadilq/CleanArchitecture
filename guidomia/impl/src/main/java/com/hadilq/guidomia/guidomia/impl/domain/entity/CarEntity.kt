package com.hadilq.guidomia.guidomia.impl.domain.entity

data class CarEntity(
  val model: ModelEntity,
  val make: MakeEntity,
  val image: ImageEntity,
  val price: PriceEntity,
  val rate: Rate,
  val pros: List<String>,
  val cons: List<String>,
)
