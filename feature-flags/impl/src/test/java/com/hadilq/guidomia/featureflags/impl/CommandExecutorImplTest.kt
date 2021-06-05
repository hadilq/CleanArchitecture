package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.featureflags.api.Command
import com.hadilq.guidomia.featureflags.api.CommandCallbackImpl
import com.hadilq.guidomia.featureflags.api.exe
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
internal class CommandExecutorImplTest {

  @Test
  fun `given_a_command then_result_command`() = runBlocking {
    val commandRegister = CommandRegisterImpl()
    val executor = CommandExecutorImpl(commandRegister, commandRegister)
    val expectedResult = FakeResultCommand()
    commandRegister.register(FakeCommand::class,
      CommandCallbackImpl(commandRegister, FakeResultCommand::class) {
        expectedResult
      })

    val result: FakeResultCommand = executor.exe(FakeCommand())

    assertEquals(expectedResult, result)
  }

  class FakeCommand : Command
  class FakeResultCommand : Command
}