package com.hadilq.guidomia.database.api

import com.hadilq.guidomia.featureflags.api.Command

class CarDataEntityCommandGetAll : Command

class CarDataEntityCommandGetAllResult(
  val result: List<CarDatabaseEntity>
) : Command
