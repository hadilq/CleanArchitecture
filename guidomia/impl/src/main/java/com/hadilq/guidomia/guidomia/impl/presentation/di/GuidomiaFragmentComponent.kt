package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.di.api.FragmentScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigatorFactory
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.MergeSubcomponent

@SingleIn(FragmentScope::class)
@MergeSubcomponent(FragmentScope::class)
interface GuidomiaFragmentComponent {

  fun guidomiaFragment(): GuidomiaFragment

  fun guidomiaNavigatorFactory(): GuidomiaNavigatorFactory
}
