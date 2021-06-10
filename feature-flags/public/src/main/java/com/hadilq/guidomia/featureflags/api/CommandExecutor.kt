/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
fun <reified IN : Command, reified OUT : Command> CommandExecutor.featureFlag(
  input: IN,
  @Suppress("UNUSED_PARAMETER") output: KClass<OUT>,
): FeatureFlag<OUT> =
  when (val commandResult: CommandResult<OUT> = exe(input)) {
    is Available<*> -> FeatureFlag.On(commandResult.command as OUT)
    else -> FeatureFlag.Off()
  }

sealed class FeatureFlag<T> {
  abstract fun <O> to(map: T.() -> O): FeatureFlag<O>

  class On<T>(val value: T) : FeatureFlag<T>() {
    override fun <O> to(map: T.() -> O): FeatureFlag<O> = On(value.map())
  }

  class Off<T> : FeatureFlag<T>() {
    override fun <O> to(map: T.() -> O): FeatureFlag<O> = Off()
  }
}
