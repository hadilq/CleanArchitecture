/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
