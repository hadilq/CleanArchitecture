package com.hadilq.guidomia.di

import com.hadilq.guidomia.CustomApplication
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component

@SingleIn(AppScope::class)
@MergeComponent(
  scope = AppScope::class,
  modules = [
    AppModule::class
  ]
)
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(customApplication: CustomApplication): Builder
    fun build(): AppComponent
  }

  fun singleActivityComponentBuilder(): SingleActivityComponent.Builder
  fun fragmentComponentBuilder(): FragmentComponent.Builder
  fun retainComponentBuilder(): RetainComponent.Builder
}
