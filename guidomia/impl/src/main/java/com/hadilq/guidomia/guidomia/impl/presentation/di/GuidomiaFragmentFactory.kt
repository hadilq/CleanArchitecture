package com.hadilq.guidomia.guidomia.impl.presentation.di

import androidx.fragment.app.Fragment
import com.hadilq.guidomia.core.api.FragmentKey
import com.hadilq.guidomia.core.api.SimpleFragmentFactory
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject
import javax.inject.Provider

@ContributesMultibinding(AppScope::class)
@FragmentKey(GuidomiaFragment::class)
class GuidomiaFragmentFactory @Inject constructor(
  private val componentBuilder: Provider<GuidomiaFragmentComponent>
) : SimpleFragmentFactory {

  override fun instantiate(): Fragment = componentBuilder.get().guidomiaFragment()
}
