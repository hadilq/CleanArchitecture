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
package com.hadilq.guidomia

import android.app.Application
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.di.AppComponent
import com.hadilq.guidomia.di.DaggerAppComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponentProvider

@SingleIn(AppScope::class)
class CustomApplication : Application(), SingleActivityComponentProvider {

  private val component: AppComponent by lazy {
    DaggerAppComponent.builder()
      .application(this)
      .build()
  }

  override val singleActivityComponentProvider: SingleActivityComponent.Builder
    get() = component.singleActivityComponentBuilder()
}