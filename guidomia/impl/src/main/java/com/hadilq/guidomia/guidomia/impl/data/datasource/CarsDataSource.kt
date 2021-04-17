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
