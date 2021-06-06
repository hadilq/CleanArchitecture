package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface Command

sealed class CommandResult<C : Command>
class Available<C : Command>(val command: C) : CommandResult<C>()
class NotAvailable<C : Command> : CommandResult<C>()

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

class CommandResultBall<C : Command>(
  val key: CommandKey,
  val command: CommandResult<C>,
  val commandClass: KClass<C>
) {

  companion object {

    @Suppress("UNCHECKED_CAST")
    inline operator fun <reified C : Command> invoke(
      key: CommandKey,
      command: C,
    ) = CommandResultBall(key, Available(command), C::class)
  }
}

interface CommandCallback<C : Command> {
  suspend fun invoke(commandBall: CommandBall<C>)
}

interface CommandResultCallback<C : Command> {
  suspend fun invoke(commandBall: CommandResultBall<C>)
}
