package com.hadilq.guidomia.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hadilq.guidomia.database.impl.entiry.CarEntity
import com.hadilq.guidomia.database.impl.entiry.ProConEntity

@Database(
  entities = [
    CarEntity::class,
    ProConEntity::class
  ],
  version = 1
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun carDao(): CarDao
}
