package com.hadilq.guidomia.guidomia.impl.data.repository

import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsCacheDataSource
import com.hadilq.guidomia.guidomia.impl.data.datasource.CarsDataSource
import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntityProvider
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

  @Test
  fun `given_empty_cache then_fetch_cars`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource)
    every { cacheDataSource.caching } returns null
    every { carsDataSource.fetchCars() } returns flowOf()

    repository.getCars().collect()

    verify(exactly = 1) { carsDataSource.fetchCars() }
  }

  @Test
  fun `given_non_empty_cache then_dont_fetch_cars`() = runBlocking {
    val repository = CarsRepository(carsDataSource, cacheDataSource)
    every { cacheDataSource.caching } returns listOf(CarEntityProvider.provide())
    every { carsDataSource.fetchCars() } returns flowOf()

    repository.getCars().collect()

    verify(exactly = 0) { carsDataSource.fetchCars() }
  }
}
