package com.hadilq.guidomia.guidomia.impl.presentation.di

import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.core.api.ViewModelKey
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject
import javax.inject.Provider

@ContributesMultibinding(AppScope::class)
@ViewModelKey(GuidomiaViewModel::class)
class GuidomiaViewModelFactory @Inject constructor(
  private val componentBuilder: Provider<GuidomiaRetainComponent>
) : SimpleViewModelFactory {

  override fun create(): ViewModel = componentBuilder.get().guidomiaViewModel()
}
