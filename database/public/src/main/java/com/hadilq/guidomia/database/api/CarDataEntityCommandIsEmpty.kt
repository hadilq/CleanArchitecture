package com.hadilq.guidomia.database.api

import com.hadilq.guidomia.featureflags.api.Command

class CarDataEntityCommandIsEmpty : Command

class CarDataEntityCommandIsEmptyResult(
  val result: Boolean
) : Command
