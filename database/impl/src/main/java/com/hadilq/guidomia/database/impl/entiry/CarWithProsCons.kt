package com.hadilq.guidomia.database.impl.entiry

import androidx.room.Embedded
import androidx.room.Relation

data class CarWithProsCons(
  @Embedded
  val car: CarEntity,

  @Relation(
    parentColumn = "id",
    entityColumn = "carId"
  )
  val proConList: List<ProConEntity>
)
