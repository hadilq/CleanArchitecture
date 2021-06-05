package com.hadilq.guidomia.featureflags.api

interface CommandHook {

  fun hookUp(commandRegister: CommandRegister)
}
