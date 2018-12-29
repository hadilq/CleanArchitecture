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
package com.gitlab.sample.domain.album.usecases

import com.gitlab.sample.domain.album.repositories.AlbumsRepository
import com.gitlab.sample.domain.common.ResultState

class GetAlbums(
        private val repository: AlbumsRepository
) {
    fun resultState(): ResultState = repository.getAlbums()
}
