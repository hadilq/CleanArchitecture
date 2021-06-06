package com.hadilq.guidomia.featureflags.impl

import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.featureflags.api.*
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import kotlin.reflect.KClass

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CommandRegisterImpl @Inject constructor() : CommandRegister by impl

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CommandResultRegisterImpl @Inject constructor() : CommandResultRegister by impl

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CommandShooterImpl @Inject constructor() : CommandShooter by impl

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
class CommandResultShooterImpl @Inject constructor() : CommandResultShooter by impl


private val impl = Impl()

private class Impl : CommandRegister, CommandResultRegister, CommandShooter, CommandResultShooter {

  private val store = mutableMapOf<KClass<Command>, MutableSet<Cmd>>()

  @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
  override fun <C : Command> register(
    commandClass: KClass<C>,
    callback: CommandCallback<C>,
  ): Registration {
    val commandClass = commandClass as KClass<Command>
    val element = RequestCmd(callback)
    store[commandClass] = store[commandClass]?.apply { add(element) } ?: mutableSetOf(element)
    return RegistrationImpl(this, callback)
  }

  @Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
  override fun <C : Command> register(
    commandClass: KClass<C>,
    key: CommandKey,
    callback: CommandResultCallback<C>
  ) {
    val commandClass = commandClass as KClass<Command>
    val element = ResultCmd(key, callback)
    store[commandClass] = store[commandClass]?.apply { add(element) } ?: mutableSetOf(element)
  }

  fun <C : Command> dispose(callback: CommandCallback<C>) {
    store.values.forEach { set ->
      set.firstOrNull { cmd ->
        when (cmd) {
          is RequestCmd<*> -> {
            cmd.callback === callback
          }
          else -> false
        }
      }?.also {
        set.remove(it)
        return
      }
    }
  }

  private fun <C : Command> disposeResult(callback: CommandResultCallback<C>) {
    store.values.forEach { set ->
      set.firstOrNull { cmd ->
        when (cmd) {
          is ResultCmd<*> -> {
            cmd.callback === callback
          }
          else -> false
        }
      }?.also {
        set.remove(it)
        return
      }
    }
  }

  @Suppress("UNCHECKED_CAST", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
  override suspend fun <C : Command> shoot(commandBall: CommandBall<C>): Boolean {
    var found = false
    store[commandBall.commandClass]?.forEach { cmd ->
      if (cmd is RequestCmd<*>) {
        (cmd.callback as CommandCallback<C>).invoke(commandBall)
        found = true
      }
    }
    return found
  }

  @Suppress("UNCHECKED_CAST", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
  override suspend fun <C : Command> shoot(commandBall: CommandResultBall<C>) {
    store[commandBall.commandClass]?.forEach { cmd ->
      if (cmd is ResultCmd<*> && cmd.key == commandBall.key) {
        (cmd.callback as CommandResultCallback<C>).invoke(commandBall)
        disposeResult(cmd.callback)
      }
    }
  }
}

private sealed class Cmd

private class RequestCmd<C : Command>(
  val callback: CommandCallback<C>,
) : Cmd()

private class ResultCmd<C : Command>(
  val key: CommandKey,
  val callback: CommandResultCallback<C>,
) : Cmd()

private class RegistrationImpl<C : Command>(
  private val disposer: Impl,
  private val callback: CommandCallback<C>,
) : Registration {

  override fun dispose() {
    disposer.dispose(callback)
  }
}
