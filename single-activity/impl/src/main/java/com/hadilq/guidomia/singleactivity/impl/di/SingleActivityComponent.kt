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
package com.hadilq.guidomia.singleactivity.impl.di

import com.hadilq.guidomia.di.api.SingleActivityScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.singleactivity.impl.SingleActivity
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent

@SingleIn(SingleActivityScope::class)
@MergeSubcomponent(SingleActivityScope::class)
interface SingleActivityComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): SingleActivityComponent
  }

  fun inject(activity: SingleActivity)
}