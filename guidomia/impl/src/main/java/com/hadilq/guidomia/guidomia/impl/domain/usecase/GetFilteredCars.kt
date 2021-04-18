package com.hadilq.guidomia.guidomia.impl.domain.usecase

import com.hadilq.guidomia.guidomia.impl.data.repository.CarsRepository
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.FilterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFilteredCars @Inject constructor(
  private val carsRepository: CarsRepository,
) {

  operator fun invoke(filterEntity: FilterEntity): Flow<List<CarEntity>> =
    carsRepository.getCars().map { list ->
      val make = filterEntity.make.value.toLowerCase()
      val model = filterEntity.model.value.toLowerCase()
      list.filter { car ->
        car.make.value.toLowerCase().contains(make) &&
          car.model.value.toLowerCase().contains(model)
      }
    }
}
