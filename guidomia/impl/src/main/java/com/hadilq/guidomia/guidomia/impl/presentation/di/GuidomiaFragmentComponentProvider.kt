package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.SingleIn
import com.squareup.anvil.annotations.ContributesTo

@[SingleIn(AppScope::class) ContributesTo(AppScope::class)]
interface GuidomiaFragmentComponentProvider {

  fun guidomiaFragmentComponentBuilder(): GuidomiaFragmentComponent.Builder
}
