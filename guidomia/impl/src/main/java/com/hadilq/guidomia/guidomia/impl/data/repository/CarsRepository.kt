package com.hadilq.guidomia.guidomia.impl.data.repository

import com.hadilq.guidomia.guidomia.impl.data.datasource.CarDatabaseDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsCacheDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsDataSource
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CarsRepository @Inject constructor(
  private val carsDataSource: CarsDataSource,
  private val cacheDataSource: CarsCacheDataSource,
  private val carDatabaseDataSource: CarDatabaseDataSource
) {

  fun getCars(): Flow<List<CarEntity>> =
    if (cacheDataSource.caching.isNullOrEmpty())
      flow {
        if (carDatabaseDataSource.isEmpty())
          emitAll(carsDataSource.fetchCars().onEach { carDatabaseDataSource.save(it) })
        else
          emit(carDatabaseDataSource.fetchCars())
      }
    else
      flowOf(cacheDataSource.caching)
}
