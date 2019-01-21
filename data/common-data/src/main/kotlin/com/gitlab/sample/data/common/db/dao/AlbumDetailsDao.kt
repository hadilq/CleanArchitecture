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
package com.gitlab.sample.data.common.db.dao

import androidx.paging.DataSource
import com.gitlab.sample.data.common.db.Database
import com.gitlab.sample.data.common.db.datasource.LimitOffsetDataSource
import com.gitlab.sample.data.common.db.entities.AlbumDetailsData
import com.gitlab.sample.data.common.db.entities.AlbumDetailsData_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.reactive.DataSubscription
import java.util.concurrent.atomic.AtomicInteger

class AlbumDetailsDao(database: Database) {

    private var dao: Box<AlbumDetailsData> = database.boxStore.boxFor()

    fun saveAlbums(albumDetails: List<AlbumDetailsData>) {
        albumDetails.forEach {
            dao.query().equal(AlbumDetailsData_.id, it.id).build().findFirst()?.apply {
                it._id = _id
            }
        }
        dao.put(albumDetails)
    }

    fun count(): Int = dao.count().toInt()

    fun getDataSourceFactory(): DataSource.Factory<Int, AlbumDetailsData> =
            object : DataSource.Factory<Int, AlbumDetailsData>() {

                private val dataHashCode = AtomicInteger(0)
                private var subscription: DataSubscription? = null

                override fun create(): DataSource<Int, AlbumDetailsData> {
                    subscription?.apply { cancel() }
                    val query = dao.query().build()
                    val dataSource = LimitOffsetDataSource(query, dataHashCode)
                    subscription = query.subscribe().weak().observer(dataSource)
                    return dataSource
                }
            }
}