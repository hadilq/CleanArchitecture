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
package com.gitlab.sample.presentation.album.di

import com.gitlab.sample.data.album.datasource.AlbumsApiSource
import com.gitlab.sample.data.album.datasource.AlbumsDataSource
import com.gitlab.sample.data.album.datasource.AlbumsDatabaseSource
import com.gitlab.sample.data.album.di.AlbumsDiNamed
import com.gitlab.sample.data.album.repositories.AlbumsRepositoryImpl
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.db.dao.AlbumDao
import com.gitlab.sample.domain.album.repositories.AlbumsRepository
import com.gitlab.sample.domain.album.usecases.GetAlbums
import com.gitlab.sample.presentation.common.ASyncTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AlbumsModule {

    @Provides
    @AlbumsScope
    @Named(AlbumsDiNamed.DATABASE_DATA_SOURCE)
    fun provideDatabaseSource(albumDao: AlbumDao): AlbumsDataSource {
        return AlbumsDatabaseSource(albumDao)
    }

    @Provides
    @AlbumsScope
    @Named(AlbumsDiNamed.API_DATA_SOURCE)
    fun provideApiSource(api: Api): AlbumsDataSource {
        return AlbumsApiSource(api)
    }

    @Provides
    @AlbumsScope
    fun provideRepository(
            @Named(AlbumsDiNamed.API_DATA_SOURCE) apiSource: AlbumsDataSource,
            @Named(AlbumsDiNamed.DATABASE_DATA_SOURCE) databaseSource: AlbumsDataSource
    ): AlbumsRepository {
        return AlbumsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    @AlbumsScope
    fun provideUseCase(
            repository: AlbumsRepository
    ): GetAlbums {
        return GetAlbums(ASyncTransformer(), repository)
    }

}
