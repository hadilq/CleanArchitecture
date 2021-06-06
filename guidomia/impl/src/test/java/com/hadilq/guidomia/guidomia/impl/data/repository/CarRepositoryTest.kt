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
package com.hadilq.guidomia.guidomia.impl.data.repository

import com.hadilq.guidomia.guidomia.impl.data.datasource.CarDatabaseDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsCacheDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsDataSource
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntityProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CarRepositoryTest {

  @MockK
  lateinit var carsDataSource: CarsDataSource

  @MockK
  lateinit var cacheDataSource: CarsCacheDataSource

  @MockK
  lateinit var carDatabaseDataSource: CarDatabaseDataSource

  @Test
  fun `given_empty_cache_and_empty_database then_fetch_cars`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource, carDatabaseDataSource)
    every { cacheDataSource.caching } returns listOf()
    coEvery { carDatabaseDataSource.isEmpty() } returns true
    coEvery { carDatabaseDataSource.availableCommand() } returns true
    every { carsDataSource.fetchCars() } returns flowOf()

    repository.getCars().collect()

    verify(exactly = 1) { carsDataSource.fetchCars() }
    coVerify(exactly = 1) { cacheDataSource.caching }
    coVerify(exactly = 0) { carDatabaseDataSource.fetchCars() }
  }

  @Test
  fun `given_empty_cache_and_not_available_database then_fetch_cars`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource, carDatabaseDataSource)
    every { cacheDataSource.caching } returns listOf()
    coEvery { carDatabaseDataSource.isEmpty() } returns true
    coEvery { carDatabaseDataSource.availableCommand() } returns false
    every { carsDataSource.fetchCars() } returns flowOf()

    repository.getCars().collect()

    verify(exactly = 1) { carsDataSource.fetchCars() }
    coVerify(exactly = 1) { cacheDataSource.caching }
    coVerify(exactly = 0) { carDatabaseDataSource.fetchCars() }
  }

  @Test
  fun `given_empty_cache_and_non_empty_database then_fetch_cars_from_database`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource, carDatabaseDataSource)
    every { cacheDataSource.caching } returns listOf()
    coEvery { carDatabaseDataSource.isEmpty() } returns false
    coEvery { carDatabaseDataSource.availableCommand() } returns true
    coEvery { carDatabaseDataSource.fetchCars() } returns listOf()

    repository.getCars().collect()

    coVerify(exactly = 0) { carsDataSource.fetchCars() }
    coVerify(exactly = 1) { cacheDataSource.caching }
    coVerify(exactly = 1) { carDatabaseDataSource.fetchCars() }
  }

  @Test
  fun `given_non_empty_cache then_dont_fetch_cars`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource, carDatabaseDataSource)
    every { cacheDataSource.caching } returns listOf(CarEntityProvider.provide())
    every { carsDataSource.fetchCars() } returns flowOf()

    repository.getCars().collect()

    verify(exactly = 0) { carsDataSource.fetchCars() }
    coVerify(exactly = 2) { cacheDataSource.caching }
    coVerify(exactly = 0) { carDatabaseDataSource.fetchCars() }
  }
}
