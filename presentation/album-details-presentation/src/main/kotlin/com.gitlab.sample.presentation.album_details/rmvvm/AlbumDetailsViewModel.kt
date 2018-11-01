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
package com.gitlab.sample.presentation.album_details.rmvvm

import android.arch.lifecycle.MutableLiveData
import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.presentation.album_details.R
import com.gitlab.sample.presentation.common.BaseViewModel
import com.gitlab.sample.presentation.common.extention.filterTo
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AlbumDetailsViewModel @Inject constructor(private val useCase: GetAlbumDetails) : BaseViewModel() {

    var albumId: Long = 0

    val albumDetailsViewState = MutableLiveData<AlbumDetailsViewState>()
    val loadingViewState = MutableLiveData<Boolean>()

    private val operated = AtomicBoolean(false)

    init {
        // Reactive way to handling View actions
        actionSteam.filterTo(GetAlbumDetailsAction::class.java)
                .filter { operated.compareAndSet(false, true) || it.force }
                .flatMap {
                    useCase.observe(albumId)
                            .doOnSubscribe { loadingViewState.value = true }
                            .doOnComplete { loadingViewState.value = false }
                            .doOnError { loadingViewState.value = false }
                }
                .map(::mapAlbumDetails)
                .onErrorReturn {
                    val value = albumDetailsViewState.value
                    ErrorAlbumViewState(
                            R.string.error_happened,
                            it,
                            (value as?GetAlbumViewState)?.photos ?: (value as?ErrorAlbumViewState)?.photos
                    )
                }
                .subscribe { albumDetailsViewState.value = it }.track()
    }

    private fun mapAlbumDetails(
            it: List<AlbumDetailsEntity>
    ): AlbumDetailsViewState = GetAlbumViewState(it)
}