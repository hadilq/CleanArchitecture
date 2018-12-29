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

import android.util.Log
import com.gitlab.sample.data.album_details.extensions.map
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.api.entities.PhotoDto
import com.gitlab.sample.data.common.api.entities.PhotosDto
import com.gitlab.sample.domain.album_details.entities.AlbumsDetailsEntity
import io.reactivex.Observable
import io.reactivex.Single

class AlbumDetailsApiSource(private val api: Api) : AlbumDetailsApiDataSource {

    override fun getAlbumDetails(albumId: Long, offset: Int, limit: Int): Single<AlbumsDetailsEntity> =
            api.getPhotos()
                    .toObservable()
                    .flatMap { Observable.fromIterable(it) }
                    .filter { it.albumId == albumId }
                    .toList()
                    .map {
                        PhotosDto(
                                it.subList(Math.min(offset, it.size), Math.min(offset + limit, it.size)),
                                it.size
                        )
                    }
                    .map { Log.d("AlbumDetailsApiSource", "list: $offset, $limit: $it"); it }
                    .map { AlbumsDetailsEntity(it.photos.map(PhotoDto::map), it.totalCount) }
}