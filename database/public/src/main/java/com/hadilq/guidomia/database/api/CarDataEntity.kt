package com.hadilq.guidomia.database.api


data class CarDatabaseEntity(
  val make: String,
  val model: String,
  val image: Int,
  val price: Float,
  val rating: Int,
  val prosList: List<String>,
  val consList: List<String>,
)
