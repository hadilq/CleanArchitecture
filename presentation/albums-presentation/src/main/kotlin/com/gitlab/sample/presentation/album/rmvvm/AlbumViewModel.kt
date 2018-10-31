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
package com.gitlab.sample.presentation.album.rmvvm

import com.gitlab.sample.domain.album.entities.AlbumEntity
import com.gitlab.sample.domain.album.usecases.GetAlbums
import com.gitlab.sample.presentation.album.R
import com.gitlab.sample.presentation.common.BaseViewModel
import com.gitlab.sample.presentation.common.di.NavigatorFactory
import com.gitlab.sample.presentation.common.extention.filterTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
        private val useCase: GetAlbums,
        private val navigatorFactory: NavigatorFactory
) : BaseViewModel() {

    // After disabling "Always up to date data" feature of LiveData(see BaseViewModel), because of its
    // downsides(Just imagine multi page GetAlbumViewState), we have to add this variable to keep the data
    // on configuration change.
    val savedAlbums = mutableListOf<AlbumEntity>()

    init {
        // Reactive way to handling View actions
        actionSteam.filterTo(AlbumClickedAction::class.java)
                .throttleFirst(
                        1000, TimeUnit.MILLISECONDS
                ) // Avoid double click(Multi view click) less than one second
                .subscribe(
                        ::albumClicked
                ) { /*If reach here log an assertion because it should never happen*/ }.track()

        actionSteam.filterTo(GetAlbumAction::class.java)
                .flatMap {
                    useCase.observe()
                            .doOnSubscribe { viewState.value = LoadingViewState(true) }
                            .doOnComplete { viewState.value = LoadingViewState(false) }
                            .doOnError { viewState.value = LoadingViewState(false) }
                            .doOnDispose { viewState.value = LoadingViewState(false) }
                }
                .map { list -> savedAlbums.addAll(list); list }
                .map { GetAlbumViewState(it) as AlbumViewState }
                .onErrorReturn { ErrorAlbumViewState(R.string.error_happened, it) }
                .subscribe { viewState.value = it }.track()
    }

    private fun albumClicked(clickedAction: AlbumClickedAction) {
        val navigator = navigatorFactory.create(AlbumDetailsNavigator::class.java)
        navigator.albumId = clickedAction.albumId
        viewState.value = NavigateViewState(navigator)
    }
}