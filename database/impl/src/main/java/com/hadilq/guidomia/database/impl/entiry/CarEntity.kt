package com.hadilq.guidomia.database.impl.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car-entity")
data class CarEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  val make: String,
  val model: String,
  val image: Int,
  val price: Float,
  val rating: Int,
)
