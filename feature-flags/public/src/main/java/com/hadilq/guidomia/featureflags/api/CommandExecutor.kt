package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface CommandExecutor {

  suspend fun <IN : Command, OUT : Command> execute(
    input: IN,
    inputClass: KClass<IN>,
    expectedOut: KClass<OUT>
  ): CommandResult<OUT>
}

suspend inline
fun <reified IN : Command, reified OUT : Command> CommandExecutor.exe(
  input: IN
): CommandResult<OUT> =
  execute(input, IN::class, OUT::class)

suspend inline
fun <reified IN : Command, reified OUT : Command> CommandExecutor.available(
  input: IN,
  @Suppress("UNUSED_PARAMETER") output: KClass<OUT>,
): OUT? =
  when (val commandResult: CommandResult<OUT> = exe(input)) {
    is Available<*> -> commandResult.command as OUT
    else -> null
  }
