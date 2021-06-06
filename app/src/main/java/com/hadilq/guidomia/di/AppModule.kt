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

import android.content.Context
import com.hadilq.guidomia.CustomApplication
import com.hadilq.guidomia.di.api.AppScope
import com.hadilq.guidomia.featureflags.api.CommandHook
import com.hadilq.guidomia.featureflags.api.CommandRegister
import com.squareup.anvil.annotations.ContributesMultibinding
import dagger.Binds
import dagger.Module
import javax.inject.Inject

@Module
interface AppModule {

  @Binds
  fun provideContext(app: CustomApplication): Context
}

@ContributesMultibinding(AppScope::class)
class EmptyHook @Inject constructor() : CommandHook {
  override fun hookUp(commandRegister: CommandRegister) {
  }
}
