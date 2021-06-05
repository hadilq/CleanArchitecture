package com.hadilq.guidomia.database.api

import com.hadilq.guidomia.featureflags.api.Command

class CarDataEntityCommandInsertAll(
  val cars: List<CarDatabaseEntity>
) : Command

class CarDataEntityCommandInsertAllResult : Command
