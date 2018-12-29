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
package com.gitlab.sample.data.album.datasource

import com.gitlab.sample.data.album.extensions.map
import com.gitlab.sample.data.common.api.Api
import com.gitlab.sample.data.common.api.entities.AlbumDto
import com.gitlab.sample.domain.album.entities.AlbumsEntity
import io.reactivex.Single

class AlbumsApiSource(private val api: Api) : AlbumsApiDataSource {

    override fun getAlbums(offset: Int, limit: Int): Single<AlbumsEntity> = api.getAlbums(offset, limit)
            .map { AlbumsEntity(it.map(AlbumDto::map), /* Hard coded in the sample app. Don't do this in product. */100) }
}