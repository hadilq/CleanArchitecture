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
package com.gitlab.sample.data.album.repositories

import com.gitlab.sample.data.album.datasource.AlbumsApiDataSource
import com.gitlab.sample.data.album.datasource.AlbumsDatabaseDataSource
import com.gitlab.sample.data.common.db.repository.LimitOffsetBoundaryCallback
import com.gitlab.sample.domain.album.repositories.AlbumsRepository
import com.gitlab.sample.domain.album.repositories.AlbumsRepository.Companion.DEFAULT_INITIAL_PAGE_MULTIPLIER
import com.gitlab.sample.domain.album.repositories.AlbumsRepository.Companion.PAGE_SIZE
import com.gitlab.sample.domain.common.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class AlbumsRepositoryImpl(
        private val apiSource: AlbumsApiDataSource,
        private val databaseSource: AlbumsDatabaseDataSource
) : AlbumsRepository {

    override fun getAlbums(): ResultState {
        val processor = PublishProcessor.create<State>()
        val disposables = CompositeDisposable()

        val callback = BoundaryCallback(apiSource, databaseSource, processor, disposables)
        return ResultState(
                processor.hide().doOnCancel { disposables.clear() },
                databaseSource.getDataSourceFactory().map { it as Entity },
                callback
        )
    }

    private class BoundaryCallback(
            private val apiSource: AlbumsApiDataSource,
            private val databaseSource: AlbumsDatabaseDataSource,
            private val processor: PublishProcessor<State>,
            private val disposables: CompositeDisposable
    ) : LimitOffsetBoundaryCallback(
            PAGE_SIZE,
            PAGE_SIZE * DEFAULT_INITIAL_PAGE_MULTIPLIER
    ) {
        override fun count(): Int = databaseSource.countAlbums()

        override fun loadingState(loading: Boolean) = processor.onNext(LoadingState(loading))

        override fun request(offset: Int, limit: Int) {
            disposables.add(
                    apiSource.getAlbums(offset, limit)
                            .subscribeOn(Schedulers.io())
                            .subscribe({ entity ->
                                processor.onNext(LoadingState(false))
                                if (offset + limit >= entity.totalCount) {
                                    endOfList()
                                }
                                processor.onNext(TotalCountState(entity.totalCount))
                                databaseSource.saveAll(entity.albums)
                            }) {
                                processor.onNext(LoadingState(false))
                                processor.onNext(ErrorState(it))
                            }
            )
        }
    }
}