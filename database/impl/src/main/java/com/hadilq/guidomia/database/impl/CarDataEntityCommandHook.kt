package com.hadilq.guidomia.database.impl

import com.hadilq.guidomia.database.api.GetCarEntityCommand
import com.hadilq.guidomia.database.api.GetCarEntityCommandResult
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.featureflags.api.*
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
class CarDataEntityCommandHook @Inject constructor(
  private val carDataEntityCommand: CarDataEntityCommandImpl,
  private val commandShooter: CommandResultShooter,
) : CommandHook {

  override fun hookUp(commandRegister: CommandRegister) {
    commandRegister.register(GetCarEntityCommand::class,
      CommandCallbackImpl(commandShooter, GetCarEntityCommandResult::class) {
        Available(GetCarEntityCommandResult(carDataEntityCommand))
      })
  }
}
