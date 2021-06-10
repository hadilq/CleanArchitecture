package com.hadilq.guidomia.guidomia.impl.presentation.di

import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@[ContributesMultibinding(AppScope::class) GuidomiaViewModelKey(GuidomiaViewModel::class)]
class GuidomiaViewModelFactory @Inject constructor(
  private val componentBuilder: GuidomiaRetainComponent.Builder
) : SimpleViewModelFactory {

  override fun create(): ViewModel = componentBuilder.build().guidomiaViewModel()
}
