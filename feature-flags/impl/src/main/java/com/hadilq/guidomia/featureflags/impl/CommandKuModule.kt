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
package com.hadilq.guidomia.featureflags.impl

import com.github.hadilq.commandku.api.*
import com.github.hadilq.commandku.impl.CommandExecutorImpl
import com.github.hadilq.commandku.impl.CommandKU
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import dagger.Provides


@ContributesTo(AppScope::class)
@Module
abstract class CommandKuModule {

  @Binds
  abstract fun commandRegister(commandKU: CommandKU): CommandRegister

  @Binds
  abstract fun commandResultRegister(commandKU: CommandKU): CommandResultRegister

  @Binds
  abstract fun commandShooter(commandKU: CommandKU): CommandShooter

  @Binds
  abstract fun commandResultShooter(commandKU: CommandKU): CommandResultShooter

  @Binds
  abstract fun commandExecutor(commandExecutorImpl: CommandExecutorImpl): CommandExecutor

  companion object {
    @Provides
    @SingleIn(AppScope::class)
    fun commandKU(): CommandKU = CommandKU()

    @Provides
    @SingleIn(AppScope::class)
    fun commandExecutorImpl(
      commandShooter: CommandShooter,
      commandResultRegister: CommandResultRegister,
    ): CommandExecutorImpl = CommandExecutorImpl(commandShooter, commandResultRegister)
  }
}
