package com.hadilq.guidomia.database.api

import com.github.hadilq.commandku.api.Command

class GetCarEntityCommand : Command

class GetCarEntityCommandResult(
  val result: CarDataEntityCommand
) : Command
