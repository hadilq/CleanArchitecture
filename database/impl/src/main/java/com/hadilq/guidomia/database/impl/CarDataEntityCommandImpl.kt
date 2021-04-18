package com.hadilq.guidomia.database.impl

import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.database.api.CarDataEntityCommand
import com.hadilq.guidomia.database.api.CarDatabaseEntity
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class CarDataEntityCommandImpl @Inject constructor(
  private val carDao: CarDao,
  private val carEntityMapper: CarEntityMapper,
) : CarDataEntityCommand {

  override suspend fun getAll(): List<CarDatabaseEntity> =
    carDao.getAll().map { carEntityMapper.map(it) }


  override suspend fun insertAll(cars: List<CarDatabaseEntity>) {
    val carEntities = carDao.insertCarEntity(
      *cars.map { carEntityMapper.mapToCarEntity(it) }.toTypedArray()
    ).mapIndexed { index, id ->
      id to cars[index]
    }.toMap()

    carDao.insertProConEntity(*carEntities.keys.flatMap { id ->
      carEntities[id]!!.prosList.map { pro ->
        carEntityMapper.mapToProConEntity(id, true, pro)
      } + carEntities[id]!!.consList.map { con ->
        carEntityMapper.mapToProConEntity(id, false, con)
      }
    }.toTypedArray())
  }

  override suspend fun isEmpty(): Boolean = carDao.getAll().isEmpty()
}
