package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface Command

inline class CommandKey(
  val key: Long,
)

class CommandBall<C : Command>(
  val key: CommandKey,
  val command: C,
  val commandClass: KClass<C>
) {

  companion object {

    @Suppress("UNCHECKED_CAST")
    inline operator fun <reified C : Command> invoke(
      key: CommandKey,
      command: C,
    ) = CommandBall(key, command, C::class)
  }
}

interface CommandCallback<C : Command> {
  suspend fun invoke(commandBall: CommandBall<C>)
}
