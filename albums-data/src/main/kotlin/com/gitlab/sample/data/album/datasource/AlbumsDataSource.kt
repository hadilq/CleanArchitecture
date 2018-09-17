package com.gitlab.sample.data.album.datasource

import com.gitlab.sample.domain.album.entities.AlbumEntity
import io.reactivex.Observable

/**
 * Created by Hadi on 9/18/18.
 */
interface AlbumsDataSource {
    fun getAlbums(): Observable<List<AlbumEntity>>

    fun saveAll(albums: List<AlbumEntity>) {}
}