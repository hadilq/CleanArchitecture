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
package com.gitlab.sample.data.common.di

import android.app.Application
import com.gitlab.sample.data.common.db.Database
import com.gitlab.sample.data.common.db.dao.AlbumDao
import com.gitlab.sample.data.common.db.dao.AlbumDetailsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Database(app)

    @Singleton
    @Provides
    fun provideAlbumDao(database: Database) = AlbumDao(database)

    @Singleton
    @Provides
    fun provideAlbumDetailsDao(database: Database) = AlbumDetailsDao(database)
}