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
package com.gitlab.sample.domain.common

import androidx.paging.DataSource
import androidx.paging.PagedList
import io.reactivex.Flowable

data class ResultState(
        val stateStream: Flowable<State>,
        val factory: DataSource.Factory<Int, Entity>,
        val boundaryCallback: PagedList.BoundaryCallback<Entity>
)

sealed class State
data class LoadingState(val loading: Boolean) : State()
data class TotalCountState(val totalCount: Int) : State()
data class ErrorState(val throwable: Throwable) : State()