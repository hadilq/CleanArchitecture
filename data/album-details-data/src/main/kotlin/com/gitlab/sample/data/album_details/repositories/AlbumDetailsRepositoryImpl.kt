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
package com.gitlab.sample.data.album_details.repositories

import com.gitlab.sample.data.album_details.datasource.AlbumDetailsApiDataSource
import com.gitlab.sample.data.album_details.datasource.AlbumDetailsDatabaseDataSource
import com.gitlab.sample.data.common.db.repository.LimitOffsetBoundaryCallback
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository.Companion.DEFAULT_INITIAL_PAGE_MULTIPLIER
import com.gitlab.sample.domain.album_details.repositories.AlbumDetailsRepository.Companion.PAGE_SIZE
import com.gitlab.sample.domain.common.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class AlbumDetailsRepositoryImpl(
        private val apiSource: AlbumDetailsApiDataSource,
        private val databaseSource: AlbumDetailsDatabaseDataSource
) : AlbumDetailsRepository {

    override fun getAlbumDetails(albumId: Long): ResultState {
        val processor = PublishProcessor.create<State>()
        val disposables = CompositeDisposable()

        val callback = BoundaryCallback(apiSource, databaseSource, processor, disposables, albumId)
        return ResultState(
                processor.hide().doOnCancel { disposables.clear() },
                databaseSource.getDataSourceFactory().map { it as Entity },
                callback
        )
    }

    private class BoundaryCallback(
            private val apiSource: AlbumDetailsApiDataSource,
            private val databaseSource: AlbumDetailsDatabaseDataSource,
            private val processor: PublishProcessor<State>,
            private val disposables: CompositeDisposable,
            private val albumId: Long
    ) : LimitOffsetBoundaryCallback(
            PAGE_SIZE,
            PAGE_SIZE * DEFAULT_INITIAL_PAGE_MULTIPLIER
    ) {
        override fun count(): Int = databaseSource.countDetails()

        override fun loadingState(loading: Boolean) = processor.onNext(LoadingState(loading))

        override fun request(offset: Int, limit: Int) {
            disposables.add(
                    apiSource.getAlbumDetails(albumId, offset, limit)
                            .subscribeOn(Schedulers.io())
                            .subscribe({ entity ->
                                processor.onNext(LoadingState(false))
                                if (offset + limit >= entity.totalCount) {
                                    endOfList()
                                }
                                processor.onNext(TotalCountState(entity.totalCount))
                                databaseSource.saveAll(entity.photos)
                            }) {
                                processor.onNext(LoadingState(false))
                                processor.onNext(ErrorState(it))
                            }
            )
        }
    }
}