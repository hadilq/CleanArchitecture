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

import com.gitlab.sample.data.album_details.datasource.AlbumDetailsApiSource
import com.gitlab.sample.data.album_details.datasource.AlbumDetailsDatabaseDataSource
import com.gitlab.sample.data.album_details.datasource.AlbumDetailsDatabaseSource
import com.gitlab.sample.data.album_details.repositories.AlbumDetailsRepositoryImpl
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.db.dao.AlbumDetailsDao
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import dagger.Module
import dagger.Provides

@Module
class AlbumDetailsModule {

    @Provides
    fun provideDatabaseSource(dao: AlbumDetailsDao): AlbumDetailsDatabaseDataSource {
        return AlbumDetailsDatabaseSource(dao)
    }

    @Provides
    fun provideApiSource(api: Api): AlbumDetailsApiSource {
        return AlbumDetailsApiSource(api)
    }

    @Provides
    fun provideRepository(
            apiSource: AlbumDetailsApiSource,
            databaseSource: AlbumDetailsDatabaseDataSource
    ): AlbumDetailsRepository {
        return AlbumDetailsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    fun provideUseCase(
            repository: AlbumDetailsRepository
    ): GetAlbumDetails {
        return GetAlbumDetails(repository)
    }
}