package com.hadilq.guidomia.guidomia.impl.domain.usecase

import com.hadilq.guidomia.guidomia.impl.domain.entity.CarEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadCars @Inject constructor() {

  operator fun invoke(): Flow<List<CarEntity>> = flow { }
}
