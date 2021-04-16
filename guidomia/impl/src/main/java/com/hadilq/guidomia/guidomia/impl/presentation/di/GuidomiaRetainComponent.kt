package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.core.api.di.GuidomiaRetainScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(GuidomiaRetainScope::class)
@MergeSubcomponent(GuidomiaRetainScope::class)
interface GuidomiaRetainComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): GuidomiaRetainComponent
  }

  fun guidomiaViewModel(): GuidomiaViewModel
}
