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
package com.gitlab.sample.data.common.db.datasource

import androidx.paging.PositionalDataSource
import android.util.Log
import io.objectbox.query.Query
import io.objectbox.reactive.DataObserver
import java.util.concurrent.atomic.AtomicInteger

class LimitOffsetDataSource<T>(
        private val query: Query<T>,
        private val dataHashCode: AtomicInteger
) : PositionalDataSource<T>(), DataObserver<List<T>> {

    override fun onData(data: List<T>) {
        val hashCode = data.hashCode()
        if (hashCode != dataHashCode.getAndSet(hashCode) && data.isNotEmpty()) {
            invalidate()
        }
    }

    override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<T?>
    ) {

        val totalCount = countItems()
        if (totalCount == 0) {
            callback.onResult(emptyList(), 0, 0)
            return
        }
        Log.d(
                "LimitOffsetDataSource",
                "loadInitial: ${params.requestedStartPosition}, ${params.requestedLoadSize}, $totalCount"
        )

        // bound the size requested, based on known count
        val firstLoadPosition = computeInitialLoadPosition(params, totalCount)
        val firstLoadSize = computeInitialLoadSize(params, firstLoadPosition, totalCount)

        val list = loadRange(firstLoadPosition, firstLoadSize)
        if (list.size == firstLoadSize) {
            callback.onResult(list, firstLoadPosition, totalCount)
        } else {
            // null list, or size doesn't match request - DB modified between count and load
            invalidate()
        }
    }

    override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<T?>
    ) {
        val list = loadRange(params.startPosition, params.loadSize)
        callback.onResult(list)
    }

    /**
     * Count number of rows query can return
     */
    private fun countItems(): Int = query.count().toInt()

    /**
     * Return the rows from startPos to startPos + loadCount
     */
    private fun loadRange(startPosition: Int, loadCount: Int): List<T> {
        Log.d("LimitOffsetDataSource", "loadRange: $startPosition, $loadCount")
        return query.find(startPosition.toLong(), loadCount.toLong())
    }
}

