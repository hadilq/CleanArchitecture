package com.hadilq.guidomia.featureflags.api

interface CommandShooter {

  suspend fun <C : Command> shoot(commandBall: CommandBall<C>): Boolean
}