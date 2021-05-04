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
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntityProvider
import com.hadilq.guidomia.guidomia.impl.domain.entity.FilterEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.MakeEntityProvider
import com.hadilq.guidomia.guidomia.impl.domain.entity.ModelEntityProvider
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetFilteredCarsTest {

  @MockK
  lateinit var carsRepository: CarsRepository

  @Test
  fun `given_match_make then_return_match`() = runBlocking {
    val getFilteredCars = GetFilteredCars(carsRepository)
    val make = "Test Make"
    val filter = FilterEntity(MakeEntityProvider.provide(make), ModelEntityProvider.provide())
    val expected = listOf(
      CarEntityProvider.provide(
        make = MakeEntityProvider.provide("complete $make name"),
        model = ModelEntityProvider.provide()
      )
    )
    every { carsRepository.getCars() } returns flowOf(expected)

    val result = getFilteredCars.invoke(filter).first()

    assert(result == expected)
  }

  @Test
  fun `given_match_model then_return_match`() = runBlocking {
    val getFilteredCars = GetFilteredCars(carsRepository)
    val model = "Test Model"
    val filter = FilterEntity(MakeEntityProvider.provide(), ModelEntityProvider.provide(model))
    val expected = listOf(
      CarEntityProvider.provide(
        make = MakeEntityProvider.provide(),
        model = ModelEntityProvider.provide("complete $model name")
      )
    )
    every { carsRepository.getCars() } returns flowOf(expected)

    val result = getFilteredCars.invoke(filter).first()

    assert(result == expected)
  }

  @Test
  fun `given_not_match_make then_return_empty`() = runBlocking {
    val getFilteredCars = GetFilteredCars(carsRepository)
    val make = "Test Make"
    val filter = FilterEntity(MakeEntityProvider.provide(make), ModelEntityProvider.provide())
    val expected = listOf(
      CarEntityProvider.provide(
        make = MakeEntityProvider.provide("Some other make"),
        model = ModelEntityProvider.provide()
      )
    )
    every { carsRepository.getCars() } returns flowOf(expected)

    val result = getFilteredCars.invoke(filter).first()

    assert(result.isEmpty())
  }

  @Test
  fun `given_not_match_model then_return_empty`() = runBlocking {
    val getFilteredCars = GetFilteredCars(carsRepository)
    val model = "Test Model"
    val filter = FilterEntity(MakeEntityProvider.provide(), ModelEntityProvider.provide(model))
    val expected = listOf(
      CarEntityProvider.provide(
        make = MakeEntityProvider.provide(),
        model = ModelEntityProvider.provide("Some other model")
      )
    )
    every { carsRepository.getCars() } returns flowOf(expected)

    val result = getFilteredCars.invoke(filter).first()

    assert(result.isEmpty())
  }
}
