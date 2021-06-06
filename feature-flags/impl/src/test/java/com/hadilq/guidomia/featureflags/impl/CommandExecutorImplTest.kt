package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.featureflags.api.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
internal class CommandExecutorImplTest {

  @Test
  fun `given_a_command then_available_result_command`() = runBlocking {
    val commandRegister = CommandRegisterImpl()
    val commandShooter = CommandShooterImpl()
    val commandResultShooter = CommandResultShooterImpl()
    val commandResultRegister = CommandResultRegisterImpl()
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
    val commandShooter = CommandShooterImpl()
    val commandResultRegister = CommandResultRegisterImpl()
    val executor = CommandExecutorImpl(commandShooter, commandResultRegister)

    val result: CommandResult<FakeResultCommand> = executor.exe(FakeCommand())

    assertTrue(result is NotAvailable<*>)
  }

  class FakeCommand : Command
  class FakeResultCommand : Command
}