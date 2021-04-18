package com.hadilq.guidomia.guidomia.impl.data.datasource

import com.hadilq.guidomia.database.api.CarDataEntityCommand
import com.hadilq.guidomia.guidomia.impl.data.mapper.CarDatabaseMapper
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import javax.inject.Inject

class CarDatabaseDataSource @Inject constructor(
  private val command: CarDataEntityCommand,
  private val mapper: CarDatabaseMapper,
) {

  suspend fun isEmpty() = command.isEmpty()

  suspend fun fetchCars(): List<CarEntity> =
    command.getAll().map { mapper.map(it) }

  suspend fun save(cars: List<CarEntity>) {
    command.insertAll(cars.map { mapper.map(it) })
  }
}
