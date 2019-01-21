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
package com.gitlab.sample.presentation.album.recycler

import androidx.recyclerview.widget.DiffUtil
import android.util.Log
import android.view.ViewGroup
import com.gitlab.sample.presentation.album.di.AlbumsScope
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolderBridge
import com.gitlab.sample.presentation.album.di.viewholder.AlbumsViewHolderFactory
import com.gitlab.sample.presentation.common.recycler.BaseAdapter
import com.gitlab.sample.presentation.common.recycler.BaseViewHolder
import com.gitlab.sample.presentation.common.recycler.RecyclerState
import javax.inject.Inject

@AlbumsScope
class AlbumAdapter @Inject constructor(
        private val factory: AlbumsViewHolderFactory,
        private val bridge: AlbumsViewHolderBridge
) : BaseAdapter(ALBUM_DIFF) {

    lateinit var onCreateViewHolder: (BaseViewHolder<*>) -> Unit
    var totalCount: Int = 0
        set(value) {
            val wasEndOfList = endOfList()
            field = value
            if (endOfList() && !wasEndOfList) {
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        bridge.parent = parent
        val viewHolder: BaseViewHolder<*> = when (viewType) {
            AlbumRecyclerState.VIEW_TYPE -> factory.create(AlbumViewHolder::class.java)
            else -> factory.create(LoadingViewHolder::class.java)
        }
        onCreateViewHolder.invoke(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        Log.d("AlbumAdapter ", "onBindViewHolder position: $position")
        val item = getItem(position)
        if (item == null) {
            // Placeholders are disabled so it never happen
        } else {
            when (item.getType()) {
                AlbumRecyclerState.VIEW_TYPE -> (holder as AlbumViewHolder).bindTo(item as AlbumRecyclerState)
                else -> (holder as LoadingViewHolder).bindTo(item as LoadingRecyclerState)
            }
        }
    }

    private fun endOfList() = totalCount <= super.getItemCount()

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if (itemCount == 0) 0 else itemCount + if (endOfList()) 0 else 1
    }

    override fun getItemViewType(position: Int): Int {
        val itemCount = itemCount
        if (itemCount != 0 && position == itemCount - 1 && !endOfList()) {
            return LoadingRecyclerState.VIEW_TYPE
        }
        return super.getItemViewType(position)
    }

    override fun getItem(position: Int): RecyclerState? {
        val itemCount = itemCount
        if (itemCount != 0 && position == itemCount - 1 && !endOfList()) {
            return LoadingRecyclerState()
        }
        return super.getItem(position)
    }

    companion object {
        val ALBUM_DIFF = object : DiffUtil.ItemCallback<RecyclerState>() {

            override fun areItemsTheSame(oldItem: RecyclerState, newItem: RecyclerState): Boolean {
                return oldItem.getType() == newItem.getType() &&
                        when (newItem.getType()) {
                            AlbumRecyclerState.VIEW_TYPE -> (oldItem as AlbumRecyclerState).album.id == (newItem as AlbumRecyclerState).album.id
                            else -> false
                        }
            }

            override fun areContentsTheSame(oldItem: RecyclerState, newItem: RecyclerState): Boolean =
                    oldItem == newItem
        }
    }
}