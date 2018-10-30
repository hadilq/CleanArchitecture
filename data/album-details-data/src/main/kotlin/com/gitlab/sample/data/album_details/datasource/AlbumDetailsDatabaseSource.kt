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
package com.gitlab.sample.data.album_details.datasource

import com.gitlab.sample.data.album_details.mapper.AlbumDetailsDataToEntityMapper
import com.gitlab.sample.data.album_details.mapper.AlbumDetailsEntityToDataMapper
import com.gitlab.sample.data.common.db.dao.AlbumDetailsDao
import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import io.reactivex.Observable

class AlbumDetailsDatabaseSource(private val albumDetailsDao: AlbumDetailsDao) : AlbumDetailsDatabaseDataSource {
    private val mapper = AlbumDetailsDataToEntityMapper()
    private val mapperReverse = AlbumDetailsEntityToDataMapper()

    override fun getAlbumDetails(albumId: Long): Observable<List<AlbumDetailsEntity>> =
            albumDetailsDao.getAlbumDetails(albumId).flatMap { mapper.observable(it) }

    override fun saveAll(albumDetails: List<AlbumDetailsEntity>) {
        albumDetailsDao.clear()
        albumDetailsDao.saveAlbums(mapperReverse.mapsFrom(albumDetails))
    }
}