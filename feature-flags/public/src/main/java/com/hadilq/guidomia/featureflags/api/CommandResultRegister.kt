package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface CommandResultRegister {

  fun <C : Command> register(
    commandClass: KClass<C>,
    key: CommandKey,
    callback: CommandCallback<C>,
  )
}