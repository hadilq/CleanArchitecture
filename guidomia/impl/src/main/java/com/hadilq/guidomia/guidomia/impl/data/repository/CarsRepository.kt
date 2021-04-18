package com.hadilq.guidomia.guidomia.impl.data.repository

import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsCacheDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsDataSource
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CarsRepository @Inject constructor(
  private val carsDataSource: CarsDataSource,
  private val cacheDataSource: CarsCacheDataSource,
) {

  fun getCars(): Flow<List<CarEntity>> =
    if (cacheDataSource.caching.isNullOrEmpty())
      carsDataSource.fetchCars()
    else
      flowOf(cacheDataSource.caching)
}
