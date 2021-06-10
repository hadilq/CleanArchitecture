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

import com.hadilq.guidomia.featureflags.api.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CommandExecutorImplTest {

  private val operation = CommandOperation()

  @Test
  fun `given_a_command then_available_result_command`() = runBlocking {
    val commandRegister = CommandRegisterImpl(operation)
    val commandShooter = CommandShooterImpl(operation)
    val commandResultShooter = CommandResultShooterImpl(operation)
    val commandResultRegister = CommandResultRegisterImpl(operation)
    val executor = CommandExecutorImpl(commandShooter, commandResultRegister)
    val expectedResult = FakeResultCommand()
    commandRegister.register(
      FakeCommand::class,
      CommandCallbackImpl(commandResultShooter, FakeResultCommand::class) {
        Available(expectedResult)
      })

    val result: CommandResult<FakeResultCommand> = executor.exe(FakeCommand())

    assertTrue(result is Available<*>)
    val command = (result as Available<FakeResultCommand>).command
    assertEquals(expectedResult, command)
  }

  @Test
  fun `given_a_command then_not_available_result_command`() = runBlocking {
    val commandShooter = CommandShooterImpl(operation)
    val commandResultRegister = CommandResultRegisterImpl(operation)
    val executor = CommandExecutorImpl(commandShooter, commandResultRegister)

    val result: CommandResult<FakeResultCommand> = executor.exe(FakeCommand())

    assertTrue(result is NotAvailable<*>)
  }

  class FakeCommand : Command
  class FakeResultCommand : Command
}