package com.hadilq.guidomia.guidomia.impl

import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.featureflags.api.*
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
      CommandCallbackImpl(commandShooter, GetGuidomiaNavigatorFactoryCommandResult::class) {
        Available(GetGuidomiaNavigatorFactoryCommandResult(factory))
      })
  }
}