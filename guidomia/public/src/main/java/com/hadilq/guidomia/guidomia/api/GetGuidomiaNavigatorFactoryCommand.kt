package com.hadilq.guidomia.guidomia.api

import com.hadilq.guidomia.featureflags.api.Command

class GetGuidomiaNavigatorFactoryCommand : Command

class GetGuidomiaNavigatorFactoryCommandResult(
  val result: GuidomiaNavigatorFactory
) : Command