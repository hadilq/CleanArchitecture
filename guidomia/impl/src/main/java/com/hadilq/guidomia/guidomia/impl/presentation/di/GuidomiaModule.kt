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
package com.hadilq.guidomia.guidomia.impl.presentation.di

import com.hadilq.guidomia.core.api.di.FragmentScope
import com.hadilq.guidomia.core.api.di.RetainScope
import com.hadilq.guidomia.guidomia.impl.presentation.CarItemFilter
import com.hadilq.guidomia.guidomia.impl.presentation.CarItemOnClick
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@[Module ContributesTo(scope = FragmentScope::class)]
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
