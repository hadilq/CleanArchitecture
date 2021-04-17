package com.hadilq.guidomia.core.api

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
  val Main: CoroutineDispatcher
  val Default: CoroutineDispatcher
  val IO: CoroutineDispatcher
}