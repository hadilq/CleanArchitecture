package com.hadilq.guidomia.database.impl.entiry

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
  tableName = "pro-con-entity",
  foreignKeys = [ForeignKey(
    entity = CarEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("carId"),
    onDelete = CASCADE,
    onUpdate = CASCADE,
  )]
)
data class ProConEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  val carId: Long,
  val pro: Boolean,
  val value: String,
)
