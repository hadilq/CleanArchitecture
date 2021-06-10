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
