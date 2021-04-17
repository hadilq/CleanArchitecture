package com.hadilq.guidomia.di.fragment

import androidx.fragment.app.Fragment
import com.hadilq.guidomia.core.api.FragmentKey
import com.hadilq.guidomia.core.api.SimpleFragmentFactory
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.hadilq.guidomia.di.FragmentComponent
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
@FragmentKey(GuidomiaFragment::class)
class GuidomiaFragmentFactory @Inject constructor(
  private val componentBuilder: FragmentComponent.Builder
) : SimpleFragmentFactory {

  override fun instantiate(): Fragment = componentBuilder.build().guidomiaFragment()
}
