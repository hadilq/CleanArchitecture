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
package com.hadilq.guidomia.di.viewmodel

import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.core.api.ViewModelKey
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.guidomia.impl.presentation.GuidomiaViewModel
import com.hadilq.guidomia.di.RetainComponent
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class)
@ViewModelKey(GuidomiaViewModel::class)
class GuidomiaViewModelFactory @Inject constructor(
  private val componentBuilder: RetainComponent.Builder
) : SimpleViewModelFactory {

  override fun create(): ViewModel = componentBuilder.build().guidomiaViewModel()
}
