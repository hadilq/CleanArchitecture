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
package com.gitlab.sample.cleanarchitecture.di.injector

import com.gitlab.sample.cleanarchitecture.App
import com.gitlab.sample.cleanarchitecture.di.app.AppComponent
import com.gitlab.sample.cleanarchitecture.di.app.AppModule
import com.gitlab.sample.cleanarchitecture.di.app.DaggerAppComponent
import com.gitlab.sample.data.common.di.DatabaseModule
import com.gitlab.sample.data.common.di.NetworkModule
import com.gitlab.sample.presentation.album.di.AlbumInjector
import com.gitlab.sample.presentation.album.di.AlbumsModule
import com.gitlab.sample.presentation.album.di.AlbumsSubComponent
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsInjector
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsModule
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsSubComponent

class InjectorFactory {
    companion object {
        fun createInjectors(app: App) = app.apply {
            AppInjector.injector = AppInjectorImpl(this)
            AlbumInjector.injector = AlbumInjectorImpl()
            AlbumDetailsInjector.injector = AlbumDetailsInjectorImpl()

            // Initiate other injectors here

        }
    }
}


class AppInjectorImpl(private val app: App) : AppInjector {

    // As long as Application is a singleton we can keep appComponent, which is also a singleton,
    // in another singleton(AppInjector) without memory leak
    override val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .networkModule(NetworkModule("https://jsonplaceholder.typicode.com/", "api key"))
                .databaseModule(DatabaseModule())
                .build()
    }
}

class AlbumInjectorImpl : AlbumInjector {
    override fun createComponent(): AlbumsSubComponent {
        return AppInjector.getAppComponent().plus(AlbumsModule())
    }
}

class AlbumDetailsInjectorImpl : AlbumDetailsInjector {
    override fun createComponent(): AlbumDetailsSubComponent {
        return AppInjector.getAppComponent().plus(AlbumDetailsModule())
    }
}