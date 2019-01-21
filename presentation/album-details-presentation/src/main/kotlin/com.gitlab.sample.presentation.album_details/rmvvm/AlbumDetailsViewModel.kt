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

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import android.util.Log
import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.domain.common.Entity
import com.gitlab.sample.domain.common.ErrorState
import com.gitlab.sample.domain.common.LoadingState
import com.gitlab.sample.domain.common.TotalCountState
import com.gitlab.sample.presentation.album_details.R
import com.gitlab.sample.presentation.album_details.recycler.AlbumPhotoRecyclerState
import com.gitlab.sample.presentation.common.BaseViewModel
import com.gitlab.sample.presentation.common.extention.filterTo
import com.gitlab.sample.presentation.common.recycler.RecyclerState
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AlbumDetailsViewModel @Inject constructor(private val useCase: GetAlbumDetails) : BaseViewModel() {

    var albumId: Long = 0

    private val viewState by lazy {
        val processor = BehaviorProcessor.create<AlbumDetailsViewState>()
        processor.onNext(AlbumDetailsViewState(null, false, Int.MAX_VALUE, 0, null))
        processor
    }

    private val operated = AtomicBoolean(false)

    private val config by lazy {
        PagedList.Config.Builder()
                .setPageSize(AlbumDetailsRepository.PAGE_SIZE)
                .setInitialLoadSizeHint(
                        AlbumDetailsRepository.PAGE_SIZE * AlbumDetailsRepository.DEFAULT_INITIAL_PAGE_MULTIPLIER
                )
                .setPrefetchDistance(AlbumDetailsRepository.PAGE_SIZE / 2)
                .setEnablePlaceholders(false)
                .build()
    }

    init {
        // Reactive way to handling View actions
        actionStream.filterTo(GetAlbumDetailsAction::class.java)
                .filter { operated.compareAndSet(false, true) || it.force }
                .toFlowable(BackpressureStrategy.LATEST)
                .flatMap(::sendEventToDeeperLayers)
                .subscribe(viewState::onNext)
                .track()
    }

    fun viewState(): Flowable<AlbumDetailsViewState> = viewState.hide()

    private fun sendEventToDeeperLayers(
            @Suppress("UNUSED_PARAMETER") action: GetAlbumDetailsAction
    ): Flowable<AlbumDetailsViewState>? {

        val resultState = useCase.resultState(albumId)
        return Flowable.merge(
                resultState.stateStream
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { state ->
                            Log.d("AlbumViewModel", "new state: $state")
                            when (state) {
                                is LoadingState -> viewState.value!!.copy(
                                        loading = state.loading, errorMessage = 0, error = null
                                )
                                is ErrorState -> {
                                    state.throwable.printStackTrace()
                                    viewState.value!!.copy(
                                            errorMessage = R.string.error_happened, error = state.throwable
                                    )
                                }
                                is TotalCountState -> {
                                    viewState.value!!.copy(totalCount = state.totalCount)
                                }
                            }
                        },
                RxPagedListBuilder(
                        resultState.factory
                                // We keep a room for having multi-entity streams in the future
                                .map { e -> e as AlbumDetailsEntity }
                                .map(::AlbumPhotoRecyclerState)
                                .map { a -> a as RecyclerState },
                        config
                ).setBoundaryCallback(resultState.boundaryCallback.map())
                        .buildFlowable(BackpressureStrategy.BUFFER)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { pagedList ->
                            viewState.value!!.copy(photos = pagedList, errorMessage = 0, error = null)
                        }
        )
    }

    private fun PagedList.BoundaryCallback<Entity>.map(): PagedList.BoundaryCallback<RecyclerState> =
            object : PagedList.BoundaryCallback<RecyclerState>() {
                override fun onZeroItemsLoaded() = (this@map::onZeroItemsLoaded)()
                override fun onItemAtEndLoaded(itemAtEnd: RecyclerState) =
                        (this@map::onItemAtEndLoaded)((itemAtEnd as AlbumPhotoRecyclerState).entity)

            }
}