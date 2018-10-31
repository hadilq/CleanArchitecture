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

import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.presentation.album_details.R
import com.gitlab.sample.presentation.common.BaseViewModel
import com.gitlab.sample.presentation.common.extention.filterTo
import javax.inject.Inject

class AlbumDetailsViewModel @Inject constructor(private val useCase: GetAlbumDetails) : BaseViewModel() {

    // After disabling "Always up to date data" feature of LiveData(see BaseViewModel), because of its
    // downsides(Just imagine multi page GetAlbumViewState), we have to add this variable to keep the data
    // on configuration change.
    val savedDetails = mutableListOf<AlbumDetailsEntity>()
    var albumId: Long = 0

    init {
        // Reactive way to handling View actions
        actionSteam.filterTo(GetAlbumDetailsAction::class.java)
                .flatMap { _ ->
                    useCase.observe(albumId)
                            .doOnSubscribe { viewState.value = LoadingViewState(true) }
                            .doOnComplete { viewState.value = LoadingViewState(false) }
                            .doOnError { viewState.value = LoadingViewState(false) }
                            .doOnDispose { viewState.value = LoadingViewState(false) }

                }
                .map { list -> savedDetails.addAll(list); list }
                .map { GetAlbumViewState(it) as AlbumDetailsViewState }
                .onErrorReturn { ErrorAlbumViewState(R.string.error_happened, it) }
                .subscribe { viewState.value = it }.track()
    }
}