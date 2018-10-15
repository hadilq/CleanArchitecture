package com.gitlab.sample.presentation.album_details.di

import com.gitlab.sample.data.album_details.datasource.AlbumDetailsApiSource
import com.gitlab.sample.data.album_details.datasource.AlbumDetailsDataSource
import com.gitlab.sample.data.album_details.datasource.AlbumDetailsDatabaseSource
import com.gitlab.sample.data.album_details.di.AlbumDetailsDiNamed
import com.gitlab.sample.data.album_details.repositories.AlbumDetailsRepositoryImpl
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.db.dao.AlbumDetailsDao
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.presentation.common.ASyncTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Hadi on 9/21/18.
 */
@Module
class AlbumDetailsModule {

    @Provides
    @AlbumDetailsScope
    @Named(AlbumDetailsDiNamed.DATABASE_DATA_SOURCE)
    fun provideDatabaseSource(dao: AlbumDetailsDao): AlbumDetailsDataSource {
        return AlbumDetailsDatabaseSource(dao)
    }

    @Provides
    @AlbumDetailsScope
    @Named(AlbumDetailsDiNamed.API_DATA_SOURCE)
    fun provideApiSource(api: Api): AlbumDetailsDataSource {
        return AlbumDetailsApiSource(api)
    }

    @Provides
    @AlbumDetailsScope
    fun provideRepository(
            @Named(AlbumDetailsDiNamed.API_DATA_SOURCE) apiSource: AlbumDetailsDataSource,
            @Named(AlbumDetailsDiNamed.DATABASE_DATA_SOURCE) databaseSource: AlbumDetailsDataSource
    ): AlbumDetailsRepository {
        return AlbumDetailsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    @AlbumDetailsScope
    fun provideUseCase(
            repository: AlbumDetailsRepository
    ): GetAlbumDetails {
        return GetAlbumDetails(ASyncTransformer(), repository)
    }
}