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

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.FragmentKey
import com.hadilq.guidomia.core.api.SimpleFragmentFactory
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.core.api.ViewModelKey
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.di.api.FragmentScope
import com.hadilq.guidomia.di.api.RetainScope
import com.hadilq.guidomia.di.api.SingleIn
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaFragment
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.squareup.anvil.annotations.ContributesMultibinding
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.MergeSubcomponent
import dagger.Subcomponent
import javax.inject.Inject

@[ContributesMultibinding(AppScope::class) FragmentKey(GuidomiaFragment::class)]
class GuidomiaFragmentFactory @Inject constructor(
  private val componentBuilder: GuidomiaFragmentComponent.Builder
) : SimpleFragmentFactory {

  override fun instantiate(): Fragment = componentBuilder.build().guidomiaFragment()
}

@[ContributesMultibinding(AppScope::class) ViewModelKey(GuidomiaViewModel::class)]
class GuidomiaViewModelFactory @Inject constructor(
  private val componentBuilder: GuidomiaRetainComponent.Builder
) : SimpleViewModelFactory {

  override fun create(): ViewModel = componentBuilder.build().guidomiaViewModel()
}

@[SingleIn(FragmentScope::class) MergeSubcomponent(FragmentScope::class)]
interface GuidomiaFragmentComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): GuidomiaFragmentComponent
  }

  fun guidomiaFragment(): GuidomiaFragment
}

@[SingleIn(RetainScope::class) MergeSubcomponent(RetainScope::class)]
interface GuidomiaRetainComponent {

  @Subcomponent.Builder
  interface Builder {
    fun build(): GuidomiaRetainComponent
  }

  fun guidomiaViewModel(): GuidomiaViewModel
}

@[SingleIn(AppScope::class) ContributesTo(AppScope::class)]
interface GuidomiaFragmentComponentProvider {

  fun guidomiaFragmentComponentBuilder(): GuidomiaFragmentComponent.Builder
}

@[SingleIn(AppScope::class) ContributesTo(AppScope::class)]
interface GuidomiaRetainComponentProvider {

  fun guidomiaRetainComponentBuilder(): GuidomiaRetainComponent.Builder
}
