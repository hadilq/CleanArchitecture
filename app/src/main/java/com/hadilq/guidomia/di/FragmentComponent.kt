package com.hadilq.guidomia.di

import com.hadilq.guidomia.core.api.di.FragmentScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(FragmentScope::class)
@MergeSubcomponent(FragmentScope::class)
interface FragmentComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): FragmentComponent
  }

  fun guidomiaFragment(): GuidomiaFragment
}
