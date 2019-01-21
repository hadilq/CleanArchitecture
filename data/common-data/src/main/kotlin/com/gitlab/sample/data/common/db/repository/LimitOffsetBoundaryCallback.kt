package com.gitlab.sample.data.common.db.repository

import androidx.paging.PagedList
import com.gitlab.sample.domain.common.Entity

abstract class LimitOffsetBoundaryCallback(
        private val pageSize: Int,
        private val initialPageSize: Int
) : PagedList.BoundaryCallback<Entity>() {
    private var offset = 0
    private var endOfList = false

    override fun onZeroItemsLoaded() {
        loadingState(true)
        if (offset != 0) {
            return
        }
        request(offset, initialPageSize)
        offset += initialPageSize
    }

    override fun onItemAtEndLoaded(itemAtEnd: Entity) {
        if (endOfList) {
            return
        }
        val count = count()
        offset += if (offset < count) {
            val limit = count + pageSize
            request(offset, limit)
            limit
        } else {
            request(offset, pageSize)
            pageSize
        }
    }

    protected fun endOfList() {
        endOfList = true
    }

    abstract fun count(): Int

    abstract fun loadingState(loading: Boolean)

    abstract fun request(offset: Int, limit: Int)
}