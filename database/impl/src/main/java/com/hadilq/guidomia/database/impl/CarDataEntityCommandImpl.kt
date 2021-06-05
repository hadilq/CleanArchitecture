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

import com.hadilq.guidomia.database.api.*
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.featureflags.api.CommandCallbackImpl
import com.hadilq.guidomia.featureflags.api.CommandHook
import com.hadilq.guidomia.featureflags.api.CommandRegister
import com.hadilq.guidomia.featureflags.api.CommandShooter
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
class CarDataEntityCommandImpl @Inject constructor(
  private val carDao: CarDao,
  private val carEntityMapper: CarEntityMapper,
  private val commandShooter: CommandShooter,
) : CommandHook {

  private suspend fun getAll(): List<CarDatabaseEntity> =
    carDao.getAll().map { carEntityMapper.map(it) }


  private suspend fun insertAll(cars: List<CarDatabaseEntity>) {
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

  private suspend fun isEmpty(): Boolean = carDao.getAll().isEmpty()

  override fun hookUp(commandRegister: CommandRegister) {

    commandRegister.register(CarDataEntityCommandGetAll::class,
      CommandCallbackImpl(commandShooter, CarDataEntityCommandGetAllResult::class) {
        CarDataEntityCommandGetAllResult(getAll())
      })

    commandRegister.register(CarDataEntityCommandInsertAll::class,
      CommandCallbackImpl(commandShooter, CarDataEntityCommandInsertAllResult::class) {
        insertAll(it.cars)
        CarDataEntityCommandInsertAllResult()
      })

    commandRegister.register(CarDataEntityCommandIsEmpty::class,
      CommandCallbackImpl(commandShooter, CarDataEntityCommandIsEmptyResult::class) {
        CarDataEntityCommandIsEmptyResult(isEmpty())
      })
  }
}
