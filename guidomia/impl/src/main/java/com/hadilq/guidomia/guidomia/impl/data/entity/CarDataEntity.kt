package com.hadilq.guidomia.guidomia.impl.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CarDataEntity(
  val make: String,
  val model: String,
  val marketPrice: Float,
  val customerPrice: Float,
  val rating: Int,
  val prosList: List<String>,
  val consList: List<String>,
)
