package com.hadilq.guidomia.di

import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.squareup.anvil.annotations.MergeComponent
import javax.inject.Singleton

@Singleton
@MergeComponent(AppScope::class)
interface AppComponent {

  fun singleActivityComponentBuilder(): SingleActivityComponent.Builder
}