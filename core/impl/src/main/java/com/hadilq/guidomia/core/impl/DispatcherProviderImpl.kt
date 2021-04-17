package com.hadilq.guidomia.core.impl

import com.hadilq.guidomia.core.api.DispatcherProvider
import com.hadilq.guidomia.core.api.di.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

  override val Main: CoroutineDispatcher
    get() = Dispatchers.Main

  override val Default: CoroutineDispatcher
    get() = Dispatchers.Default

  override val IO: CoroutineDispatcher
    get() = Dispatchers.IO
}