package com.hadilq.guidomia.featureflags.api

import kotlin.reflect.KClass

interface CommandExecutor {

  suspend fun <IN : Command, OUT : Command> execute(
    input: IN,
    inputClass: KClass<IN>,
    expectedOut: KClass<OUT>
  ): OUT
}

suspend inline fun <reified IN : Command, reified OUT : Command> CommandExecutor.exe(input: IN): OUT =
  execute(input, IN::class, OUT::class)