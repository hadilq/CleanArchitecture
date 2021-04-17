package com.hadilq.guidomia.di.viewmodel

import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.core.api.ViewModelKey
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.hadilq.guidomia.di.RetainComponent
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
@ViewModelKey(GuidomiaViewModel::class)
class GuidomiaViewModelFactory @Inject constructor(
  private val componentBuilder: RetainComponent.Builder
) : SimpleViewModelFactory {

  override fun create(): ViewModel = componentBuilder.build().guidomiaViewModel()
}
