/***
 * Copyright 2018 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.gitlab.sample.cleanarchitecture.di.app

import android.app.Application
import android.content.Context
import com.gitlab.sample.cleanarchitecture.App
import com.gitlab.sample.cleanarchitecture.di.FragmentFactory
import com.gitlab.sample.presentation.common.BaseFragmentFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Singleton
    @Provides
    fun provideApp(): App {
        return application
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideBaseFragmentFactory(): BaseFragmentFactory {
        return FragmentFactory()
    }
}