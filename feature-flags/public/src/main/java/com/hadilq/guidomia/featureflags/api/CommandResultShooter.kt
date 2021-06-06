package com.hadilq.guidomia.featureflags.api

interface CommandResultShooter {

  suspend fun <C : Command> shoot(commandBall: CommandResultBall<C>)
}