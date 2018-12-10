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

import android.util.Log
import com.gitlab.sample.domain.album.entities.AlbumEntity
import com.gitlab.sample.domain.album.usecases.GetAlbums
import com.gitlab.sample.presentation.album.R
import com.gitlab.sample.presentation.common.BaseViewModel
import com.gitlab.sample.presentation.common.Navigator
import com.gitlab.sample.presentation.common.di.NavigatorFactory
import com.gitlab.sample.presentation.common.extention.filterTo
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
        private val useCase: GetAlbums,
        private val navigatorFactory: NavigatorFactory
) : BaseViewModel() {

    private val navigateViewState = PublishProcessor.create<Navigator>()
    private val loadingViewState = BehaviorProcessor.create<Boolean>()
    private val albumViewState = BehaviorProcessor.create<AlbumViewState>()

    private val operated = AtomicBoolean(false)

    init {
        // Reactive way to handling View actions
        actionStream.filterTo(AlbumClickedAction::class.java)
                .throttleFirst(1000, TimeUnit.MILLISECONDS) // Avoid double click(Multi view click) less than one second
                .subscribe(::albumClicked) { /*If reach here log an assertion because it should never happen*/ }.track()

        actionStream.filterTo(GetAlbumAction::class.java)
                .filter { operated.compareAndSet(false, true) || it.force }
                .flatMap {
                    Log.e("AlbumViewModel", "usecase is called")

                    useCase.observe()
                            .doOnSubscribe { loadingViewState.onNext(true) }
                            .doOnComplete { loadingViewState.onNext(false) }
                            .doOnError { loadingViewState.onNext(false) }
                }
                .map(::mapAlbums)
                .onErrorReturn {
                    val value = albumViewState.value
                    ErrorAlbumViewState(
                            R.string.error_happened,
                            it,
                            (value as?GetAlbumViewState)?.albums ?: (value as?ErrorAlbumViewState)?.albums
                    )
                }
                .subscribe { albumViewState.onNext(it) }.track()
    }

    fun loadingViewState(): Flowable<Boolean> = loadingViewState.hide()
    fun albumViewState(): Flowable<AlbumViewState> = albumViewState.hide()
    fun navigateViewState(): Flowable<Navigator> = navigateViewState.hide()

    private fun mapAlbums(
            it: List<AlbumEntity>
    ): AlbumViewState = GetAlbumViewState(it)

    private fun albumClicked(clickedAction: AlbumClickedAction) {
        val navigator = navigatorFactory.create(AlbumDetailsNavigator::class.java)
        navigator.albumId = clickedAction.albumId
        navigateViewState.onNext(navigator)
    }
}