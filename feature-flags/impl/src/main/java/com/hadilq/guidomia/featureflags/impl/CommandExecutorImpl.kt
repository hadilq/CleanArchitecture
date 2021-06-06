package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.featureflags.api.*
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random
import kotlin.reflect.KClass

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CommandExecutorImpl @Inject constructor(
  private val commandShooter: CommandShooter,
  private val commandResultRegister: CommandResultRegister,
) : CommandExecutor {

  private val random = Random(Random.nextLong())

  @Suppress("UNCHECKED_CAST", "BlockingMethodInNonBlockingContext")
  override suspend fun <IN : Command, OUT : Command> execute(
    input: IN,
    inputClass: KClass<IN>,
    expectedOut: KClass<OUT>,
  ): CommandResult<OUT> = suspendCoroutine { con: Continuation<CommandResult<OUT>> ->
    val newCommandKey = getNewCommandKey()
    commandResultRegister.register(expectedOut, newCommandKey, CommandCallbackImpl(con))
    runBlocking(con.context) {
      if (!commandShooter.shoot(CommandBall(newCommandKey, input, inputClass))) {
        con.resume(NotAvailable())
      }
    }
  }

  private fun getNewCommandKey(): CommandKey = CommandKey(random.nextLong())
}

private class CommandCallbackImpl<C : Command>(
  private val con: Continuation<CommandResult<C>>,
) : CommandResultCallback<C> {

  override suspend fun invoke(commandBall: CommandResultBall<C>) {
    con.resume(commandBall.command)
  }
}

