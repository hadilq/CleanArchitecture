package com.hadilq.guidomia.di

import com.hadilq.guidomia.core.api.di.RetainScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(RetainScope::class)
@MergeSubcomponent(RetainScope::class)
interface RetainComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): RetainComponent
  }

  fun guidomiaViewModel(): GuidomiaViewModel
}
