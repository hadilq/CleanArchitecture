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
package com.gitlab.sample.cleanarchitecture.di.navigator

import android.os.Bundle
import androidx.navigation.NavController
import com.gitlab.sample.presentation.album.AlbumFragmentDirections
import com.gitlab.sample.presentation.album_details.AlbumDetailsFragmentArgs
import com.gitlab.sample.presentation.common.navigator.AlbumDetailsNavigator
import javax.inject.Inject

class AlbumDetailsNavigatorImpl @Inject constructor() : AlbumDetailsNavigator {
    var id: Long = 0

    override fun setAlbumId(albumId: Long) {
        this.id = albumId
    }

    override fun getAlbumId(arguments: Bundle?): Long {
        return AlbumDetailsFragmentArgs.fromBundle(arguments!!).albumId.toLong()
    }

    override fun launchFragment(nav: NavController) {
        nav.navigate(AlbumFragmentDirections.actionAlbumDetails(id.toInt()))
    }
}