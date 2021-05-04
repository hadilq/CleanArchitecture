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
package com.hadilq.guidomia.guidomia.impl.data.mapper

import com.hadilq.guidomia.database.api.CarDatabaseEntity
import com.hadilq.guidomia.guidomia.impl.domain.entity.*
import javax.inject.Inject

class CarDatabaseMapper @Inject constructor() {

  fun map(car: CarDatabaseEntity) = with(car) {
    CarEntity(
      make = MakeEntity(make),
      model = ModelEntity(model),
      image = ImageEntity(image),
      price = PriceEntity(price),
      rate = Rate.values()[rating - 1],
      pros = prosList,
      cons = consList,
    )
  }

  fun map(car: CarEntity) = with(car) {
    CarDatabaseEntity(
      make = make.value,
      model = model.value,
      image = image.value,
      price = price.value,
      rating = rate.value,
      prosList = pros,
      consList = cons,
    )
  }
}
