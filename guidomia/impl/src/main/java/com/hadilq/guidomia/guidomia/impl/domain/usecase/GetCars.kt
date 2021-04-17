package com.hadilq.guidomia.guidomia.impl.domain.usecase

import com.hadilq.guidomia.guidomia.impl.data.repository.CarsRepository
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCars @Inject constructor(
  private val carsRepository: CarsRepository,
) {

  operator fun invoke(): Flow<List<CarEntity>> = carsRepository.getCars()
}
