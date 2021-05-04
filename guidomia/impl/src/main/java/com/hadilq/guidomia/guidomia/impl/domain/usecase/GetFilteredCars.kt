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
