package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.core.api.di.FragmentScope
import com.hadilq.guidomia.core.api.di.RetainScope
import com.hadilq.guidomia.guidomia.impl.presentation.CarItemFilter
import com.hadilq.guidomia.guidomia.impl.presentation.CarItemOnClick
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(scope = FragmentScope::class)
object GuidomiaModule {

  /**
   * The lifecycle of [RetainScope] is longer than the lifecycle of [FragmentScope],
   * so without any leak we can pass objects of [RetainScope] to [FragmentScope].
   */
  @Provides
  fun provideCarItemOnClick(fragment: GuidomiaFragment): CarItemOnClick =
    fragment.viewModel

  /**
   * The lifecycle of [RetainScope] is longer than the lifecycle of [FragmentScope],
   * so without any leak we can pass objects of [RetainScope] to [FragmentScope].
   */
  @Provides
  fun provideCarItemFilter(fragment: GuidomiaFragment): CarItemFilter =
    fragment.viewModel
}
