package com.gitlab.sample.data.album.datasource

import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.album.mapper.AlbumDtoToEntityMapper
import com.gitlab.sample.domain.album.entities.AlbumEntity
import io.reactivex.Observable

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumsApiSource(private val api: Api) : AlbumsDataSource {
    private val mapper = AlbumDtoToEntityMapper()

    override fun getAlbums(): Observable<List<AlbumEntity>> = api.getAlbums()
            .toObservable()
            .flatMap { mapper.observable(it) }

}