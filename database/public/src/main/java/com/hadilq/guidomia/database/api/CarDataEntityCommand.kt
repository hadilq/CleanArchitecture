package com.hadilq.guidomia.database.api

interface CarDataEntityCommand {

  suspend fun getAll(): List<CarDatabaseEntity>

  suspend fun insertAll(cars: List<CarDatabaseEntity>)

  suspend fun isEmpty(): Boolean
}
