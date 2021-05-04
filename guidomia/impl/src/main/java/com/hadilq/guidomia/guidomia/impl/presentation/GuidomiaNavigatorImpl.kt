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
package com.hadilq.guidomia.guidomia.impl.presentation

import androidx.appcompat.app.AppCompatActivity
import com.hadilq.guidomia.core.api.FragmentFactory
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigator
import com.hadilq.guidomia.guidomia.api.GuidomiaNavigatorFactory
import com.hadilq.guidomia.singleactivity.api.NavigatorFactory
import com.squareup.anvil.annotations.ContributesBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@ContributesBinding(AppScope::class)
@AssistedFactory
interface GuidomiaNavigatorFactoryImpl : GuidomiaNavigatorFactory {

  override fun create(activity: AppCompatActivity): GuidomiaNavigatorImpl
}

class GuidomiaNavigatorImpl @AssistedInject constructor(
  private val navigatorFactory: NavigatorFactory,
  private val fragmentFactory: FragmentFactory,
  @Assisted private val activity: AppCompatActivity
) : GuidomiaNavigator {

  override fun commit() {
    navigatorFactory.create(activity)
      .commit(fragmentFactory.instantiate(GuidomiaFragment::class))
  }
}
