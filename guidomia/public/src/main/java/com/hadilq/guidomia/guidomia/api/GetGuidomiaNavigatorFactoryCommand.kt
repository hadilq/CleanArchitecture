package com.hadilq.guidomia.guidomia.api

import com.github.hadilq.commandku.api.Command

class GetGuidomiaNavigatorFactoryCommand : Command

class GetGuidomiaNavigatorFactoryCommandResult(
  val result: GuidomiaNavigatorFactory
) : Command