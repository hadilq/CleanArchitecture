package com.gitlab.sample.data.album.repositories

import com.gitlab.sample.data.album.datasource.AlbumsDataSource
import com.gitlab.sample.data.album.di.AlbumsDiNamed
import com.gitlab.sample.domain.album.entities.AlbumEntity
import com.gitlab.sample.domain.album.repositories.AlbumsRepository
import io.reactivex.Observable
import javax.inject.Named

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumsRepositoryImpl(
        @Named(AlbumsDiNamed.API_DATA_SOURCE) private val apiSource: AlbumsDataSource,
        @Named(AlbumsDiNamed.DATABASE_DATA_SOURCE) private val databaseSource: AlbumsDataSource
) : AlbumsRepository {

    override fun getAlbums(): Observable<List<AlbumEntity>> =
            databaseSource.getAlbums().flatMap {
                if (it.isEmpty()) {
                    apiSource.getAlbums().map { list -> databaseSource.saveAll(list); list }
                } else {
                    Observable.just(it)
                }
            }
}