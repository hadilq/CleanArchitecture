package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface CommandRegister {

  fun <C : Command> register(
    commandClass: KClass<C>,
    callback: CommandCallback<C>,
  ): Registration
}

interface Registration {
  fun dispose()
}

class CommandCallbackImpl<IN : Command, OUT : Command>(
  private val commandShooter: CommandShooter,
  private val commandResultClass:KClass<OUT>,
  private val result: suspend (IN) -> OUT,
) : CommandCallback<IN> {

  override suspend fun invoke(commandBall: CommandBall<IN>) {
    commandShooter.shoot(
      CommandBall(
        commandBall.key,
        result(commandBall.command),
        commandResultClass
      )
    )
  }
}
