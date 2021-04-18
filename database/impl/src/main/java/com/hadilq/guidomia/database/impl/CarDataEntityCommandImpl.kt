/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
