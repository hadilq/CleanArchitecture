package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.core.api.di.GuidomiaFragmentScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(GuidomiaFragmentScope::class)
@MergeSubcomponent(GuidomiaFragmentScope::class)
interface GuidomiaFragmentComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): GuidomiaFragmentComponent
  }

  fun guidomiaFragment(): GuidomiaFragment
}
