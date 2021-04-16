package com.hadilq.guidomia.singleactivity.impl.di

import com.hadilq.guidomia.core.api.di.DaggerSingleActivityScope
import com.hadilq.guidomia.core.api.di.SingleActivityScope
import com.hadilq.guidomia.singleactivity.impl.SingleActivity
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@DaggerSingleActivityScope
@MergeSubcomponent(SingleActivityScope::class)
interface SingleActivityComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): SingleActivityComponent
  }

  fun inject(activity: SingleActivity)
}