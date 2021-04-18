package com.hadilq.guidomia.database.impl

import androidx.room.*
import com.hadilq.guidomia.database.impl.entiry.CarEntity
import com.hadilq.guidomia.database.impl.entiry.CarWithProsCons
import com.hadilq.guidomia.database.impl.entiry.ProConEntity

@Dao
interface CarDao {

  @Transaction
  @Query("SELECT * FROM `car-entity`")
  suspend fun getAll(): List<CarWithProsCons>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCarEntity(vararg car: CarEntity): Array<Long>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertProConEntity(vararg proCon: ProConEntity): Array<Long>

  @Delete
  suspend fun delete(car: CarEntity)
}
