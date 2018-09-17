package com.gitlab.sample.data.album.datasource

import com.gitlab.sample.data.common.db.dao.AlbumDao
import com.gitlab.sample.data.album.mapper.AlbumDataToEntityMapper
import com.gitlab.sample.data.album.mapper.AlbumEntityToDataMapper
import com.gitlab.sample.domain.album.entities.AlbumEntity
import io.reactivex.Observable

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumsDatabaseSource(private val albumDao: AlbumDao) : AlbumsDataSource {
    private val mapper = AlbumDataToEntityMapper()
    private val mapperReverse = AlbumEntityToDataMapper()

    override fun getAlbums(): Observable<List<AlbumEntity>> = albumDao.getAlbums()
            .flatMap { mapper.observable(it) }

    override fun saveAll(albums: List<AlbumEntity>) {
        albumDao.clear()
        albumDao.saveAlbums(mapperReverse.mapsFrom(albums))
    }
}