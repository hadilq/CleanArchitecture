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
package com.hadilq.guidomia.guidomia.impl

import com.github.hadilq.commandku.api.CommandCallbackImpl
import com.github.hadilq.commandku.api.CommandHook
import com.github.hadilq.commandku.api.CommandRegister
import com.github.hadilq.commandku.api.CommandResultShooter
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.guidomia.api.GetGuidomiaNavigatorFactoryCommand
import com.hadilq.guidomia.guidomia.api.GetGuidomiaNavigatorFactoryCommandResult
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigatorFactory
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
class GuidomiaCommandHook @Inject constructor(
  private val factory: GuidomiaNavigatorFactory,
  private val commandShooter: CommandResultShooter,
) : CommandHook {

  override fun hookUp(commandRegister: CommandRegister) {
    commandRegister.register(GetGuidomiaNavigatorFactoryCommand::class,
      CommandCallbackImpl(commandShooter) {
        GetGuidomiaNavigatorFactoryCommandResult(factory)
      })
  }
}