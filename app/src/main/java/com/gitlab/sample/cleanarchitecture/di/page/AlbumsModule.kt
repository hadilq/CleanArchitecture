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
package com.gitlab.sample.cleanarchitecture.di.page

import com.gitlab.sample.data.album.datasource.AlbumsApiDataSource
import com.gitlab.sample.data.album.datasource.AlbumsApiSource
import com.gitlab.sample.data.album.datasource.AlbumsDatabaseDataSource
import com.gitlab.sample.data.album.datasource.AlbumsDatabaseSource
import com.gitlab.sample.data.album.repositories.AlbumsRepositoryImpl
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.db.dao.AlbumDao
import com.gitlab.sample.domain.album.repositories.AlbumsRepository
import com.gitlab.sample.domain.album.usecases.GetAlbums
import dagger.Module
import dagger.Provides

@Module
class AlbumsModule {

    @Provides
    fun provideDatabaseSource(albumDao: AlbumDao): AlbumsDatabaseDataSource {
        return AlbumsDatabaseSource(albumDao)
    }

    @Provides
    fun provideApiSource(api: Api): AlbumsApiDataSource {
        return AlbumsApiSource(api)
    }

    @Provides
    fun provideRepository(
            apiSource: AlbumsApiDataSource,
            databaseSource: AlbumsDatabaseDataSource
    ): AlbumsRepository {
        return AlbumsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    fun provideUseCase(
            repository: AlbumsRepository
    ): GetAlbums {
        return GetAlbums(repository)
    }
}
