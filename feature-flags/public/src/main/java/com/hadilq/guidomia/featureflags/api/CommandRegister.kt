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
  private val commandShooter: CommandResultShooter,
  private val commandResultClass: KClass<OUT>,
  private val result: suspend (IN) -> CommandResult<OUT>,
) : CommandCallback<IN> {

  override suspend fun invoke(commandBall: CommandBall<IN>) {
    commandShooter.shoot(
      CommandResultBall(
        commandBall.key,
        result(commandBall.command),
        commandResultClass
      )
    )
  }
}
