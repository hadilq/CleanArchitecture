package com.hadilq.guidomia.di

import com.hadilq.guidomia.CustomApplication
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.di.GuidomiaFragmentComponent
import com.hadilq.guidomia.guidomia.impl.presentation.di.GuidomiaRetainComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
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
  fun guidomiaFragmentComponentBuilder(): GuidomiaFragmentComponent.Builder
  fun guidomiaRetainComponentBuilder(): GuidomiaRetainComponent.Builder
}
