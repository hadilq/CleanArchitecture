package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.di.api.FragmentScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@[SingleIn(FragmentScope::class) MergeSubcomponent(FragmentScope::class)]
interface GuidomiaFragmentComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): GuidomiaFragmentComponent
  }

  fun guidomiaFragment(): GuidomiaFragment
}
