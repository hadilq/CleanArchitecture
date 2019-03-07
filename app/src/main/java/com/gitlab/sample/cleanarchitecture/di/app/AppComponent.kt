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

import com.gitlab.sample.cleanarchitecture.App
import com.gitlab.sample.cleanarchitecture.di.navigator.NavigatorModule
import com.gitlab.sample.cleanarchitecture.di.page.AlbumDetailsModule
import com.gitlab.sample.cleanarchitecture.di.page.AlbumsModule
import com.gitlab.sample.cleanarchitecture.di.viewmodel.ViewModelModule
import com.gitlab.sample.data.common.di.DatabaseModule
import com.gitlab.sample.data.common.di.NetworkModule
import com.gitlab.sample.presentation.album.di.AlbumsFragmentModule
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsFragmentModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            NetworkModule::class,
            DatabaseModule::class,
            MainActivityModule::class,
            ViewModelModule::class,
            NavigatorModule::class,
            AlbumsFragmentModule::class,
            AlbumsModule::class,
            AlbumDetailsFragmentModule::class,
            AlbumDetailsModule::class
        ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}