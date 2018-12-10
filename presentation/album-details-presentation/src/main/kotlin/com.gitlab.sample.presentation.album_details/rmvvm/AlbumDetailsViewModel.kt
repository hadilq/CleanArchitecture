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
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AlbumDetailsViewModel @Inject constructor(private val useCase: GetAlbumDetails) : BaseViewModel() {

    var albumId: Long = 0

    private val albumDetailsViewState = BehaviorProcessor.create<AlbumDetailsViewState>()
    private val loadingViewState = BehaviorProcessor.create<Boolean>()

    private val operated = AtomicBoolean(false)

    init {
        // Reactive way to handling View actions
        actionStream.filterTo(GetAlbumDetailsAction::class.java)
                .filter { operated.compareAndSet(false, true) || it.force }
                .flatMap {
                    useCase.observe(albumId)
                            .doOnSubscribe { loadingViewState.onNext(true) }
                            .doOnComplete { loadingViewState.onNext(false) }
                            .doOnError { loadingViewState.onNext(false) }
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
                .subscribe { albumDetailsViewState.onNext(it) }.track()
    }

    fun albumDetailsViewState(): Flowable<AlbumDetailsViewState> = albumDetailsViewState.hide()
    fun loadingViewState(): Flowable<Boolean> = loadingViewState.hide()

    private fun mapAlbumDetails(
            it: List<AlbumDetailsEntity>
    ): AlbumDetailsViewState = GetAlbumViewState(it)
}