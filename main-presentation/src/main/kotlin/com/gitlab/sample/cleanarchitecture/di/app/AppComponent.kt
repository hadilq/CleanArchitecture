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

import com.gitlab.sample.cleanarchitecture.MainNavigator
import com.gitlab.sample.data.common.di.DatabaseModule
import com.gitlab.sample.data.common.di.NetworkModule
import com.gitlab.sample.presentation.album.di.AlbumsModule
import com.gitlab.sample.presentation.album.di.AlbumsSubComponent
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsModule
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsSubComponent
import com.gitlab.sample.presentation.common.di.BaseComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            NetworkModule::class,
            DatabaseModule::class
        ]
)
interface AppComponent : BaseComponent {
    fun plus(module: AlbumsModule): AlbumsSubComponent
    fun plus(module: AlbumDetailsModule): AlbumDetailsSubComponent

    fun inject(mainNavigator: MainNavigator)
}