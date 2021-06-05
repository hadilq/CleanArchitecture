package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.featureflags.api.*
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

  private val random = Random(2345234L)

  @Suppress("UNCHECKED_CAST", "BlockingMethodInNonBlockingContext")
  override suspend fun <IN : Command, OUT : Command> execute(
    input: IN,
    inputClass: KClass<IN>,
    expectedOut: KClass<OUT>
  ): OUT = suspendCoroutine { con ->
    val newCommandKey = getNewCommandKey()
    commandResultRegister.register(expectedOut, newCommandKey, CommandCallbackImpl(con))
    runBlocking(con.context) {
      commandShooter.shoot(CommandBall(newCommandKey, input, inputClass))
    }
  }

  private fun getNewCommandKey(): CommandKey = CommandKey(random.nextLong())
}

private class CommandCallbackImpl<C : Command>(
  private val con: Continuation<C>,
) : CommandCallback<C> {
  override suspend fun invoke(commandBall: CommandBall<C>) = withContext(Dispatchers.IO) {
    con.resume(commandBall.command)
  }
}

