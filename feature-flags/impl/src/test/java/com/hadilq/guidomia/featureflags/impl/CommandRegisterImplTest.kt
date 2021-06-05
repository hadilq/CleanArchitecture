package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.featureflags.api.Command
import com.hadilq.guidomia.featureflags.api.CommandBall
import com.hadilq.guidomia.featureflags.api.CommandCallback
import com.hadilq.guidomia.featureflags.api.CommandKey
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

@ExtendWith(MockKExtension::class)
internal class CommandRegisterImplTest {

  @Test
  fun `given_a_callback then_shoot_command`() = runBlocking {
    val commandRegisterImpl = CommandRegisterImpl()
    val commandBall = CommandBall(CommandKey(123), FakeCommand())
    val callback = FakeCommandCallback<FakeCommand>()

    commandRegisterImpl.register(FakeCommand::class, callback)
    commandRegisterImpl.shoot(commandBall)

    assertEquals(commandBall, callback.commandBall)
  }

  @Test
  fun `given_a_callback_register_dispose then_shoot_command`() = runBlocking {
    val commandRegisterImpl = CommandRegisterImpl()
    val commandBall = CommandBall(CommandKey(123), FakeCommand())
    val callback = FakeCommandCallback<FakeCommand>()

    val registration = commandRegisterImpl.register(FakeCommand::class, callback)
    registration.dispose()
    commandRegisterImpl.shoot(commandBall)

    assertNull(callback.commandBall)
  }

  @Test
  fun `given_a_callback_register_result then_shoot_command`() = runBlocking {
    val commandRegisterImpl = CommandRegisterImpl()
    val key = 1243L
    val commandBall = CommandBall(CommandKey(key), FakeCommand())
    val callback = FakeCommandCallback<FakeCommand>()

    commandRegisterImpl.register(FakeCommand::class, CommandKey(key), callback)
    commandRegisterImpl.shoot(commandBall)

    assertEquals(commandBall, callback.commandBall)
  }

  @Test
  fun `given_a_callback_register_result then_shoot_command_twice`() = runBlocking {
    val commandRegisterImpl = CommandRegisterImpl()
    val key = 1243L
    val commandBall = CommandBall(CommandKey(key), FakeCommand())
    val callback = FakeCommandCallback<FakeCommand>()

    commandRegisterImpl.register(FakeCommand::class, CommandKey(key), callback)
    commandRegisterImpl.shoot(commandBall)

    assertEquals(commandBall, callback.commandBall)
    callback.commandBall = null

    commandRegisterImpl.shoot(commandBall)

    assertNull(callback.commandBall)
  }

  @Test
  fun `given_a_callback_register_result then_shoot_command_with_different_key`() = runBlocking {
    val commandRegisterImpl = CommandRegisterImpl()
    val key1 = 1243L
    val key2 = 322L
    val commandBall = CommandBall(CommandKey(key1), FakeCommand())
    val callback = FakeCommandCallback<FakeCommand>()

    commandRegisterImpl.register(FakeCommand::class, CommandKey(key2), callback)
    commandRegisterImpl.shoot(commandBall)

    assertNotEquals(key1, key2)
    assertNull(callback.commandBall)
  }


  class FakeCommand : Command

  class FakeCommandCallback<C : Command> : CommandCallback<C> {

    var commandBall: CommandBall<C>? = null

    override suspend fun invoke(commandBall: CommandBall<C>) {
      this.commandBall = commandBall
    }
  }
}