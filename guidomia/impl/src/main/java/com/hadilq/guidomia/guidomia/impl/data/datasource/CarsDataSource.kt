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
package com.hadilq.guidomia.guidomia.impl.data.datasource

import android.content.Context
import com.hadilq.guidomia.core.api.DispatcherProvider
import com.hadilq.guidomia.guidomia.impl.data.entity.CarDataEntity
import com.hadilq.guidomia.guidomia.impl.data.mapper.CarDataMapper
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CarsDataSource @Inject constructor(
  private val context: Context,
  private val carDataMapper: CarDataMapper,
  private val dispatcherProvider: DispatcherProvider,
) {

  fun fetchCars(): Flow<List<CarEntity>> = flow {
    @Suppress("BlockingMethodInNonBlockingContext")
    val json = withContext(dispatcherProvider.IO) {
      context.assets.open(JSON_FILE_NAME).bufferedReader().use { it.readText() }
    }

    emit(Json.decodeFromString<List<CarDataEntity>>(json).map { carDataMapper.map(it) })
  }
}

private const val JSON_FILE_NAME = "car_list.json"
