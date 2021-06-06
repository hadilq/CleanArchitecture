package com.hadilq.guidomia.database.api

import com.hadilq.guidomia.featureflags.api.Command

class GetCarEntityCommand : Command

class GetCarEntityCommandResult(
  val result: CarDataEntityCommand
) : Command
