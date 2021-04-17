package com.hadilq.guidomia.guidomia.impl.data.repository

import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsDataSource
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarsRepository @Inject constructor(
  private val carsDataSource: CarsDataSource,
) {

  fun getCars(): Flow<List<CarEntity>> = carsDataSource.fetchCars()
}
