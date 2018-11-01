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
package com.gitlab.sample.presentation.album_details.recycler

import android.view.View
import android.widget.ImageView
import com.gitlab.sample.presentation.album_details.di.viewholder.AlbumViewHolderBridge
import com.gitlab.sample.presentation.album_details.di.viewholder.AlbumViewHolder
import com.gitlab.sample.presentation.common.extention.inflate
import com.gitlab.sample.presentation.common.extention.loadFromUrl
import com.gitlab.sample.presentation.common.recycler.BaseViewHolder
import javax.inject.Inject

class AlbumPhotoViewHolder(
        view: View,
        private val itemWidth: Int
) : BaseViewHolder<AlbumPhotoViewData>(view), AlbumViewHolder {

    @Inject
    constructor(bridge: AlbumViewHolderBridge) : this(
            bridge.parent.inflate(AlbumPhotoViewData.VIEW_TYPE),
            bridge.itemWidth
    )

    init {
        itemView.layoutParams.height = itemWidth
        itemView.layoutParams = itemView.layoutParams
    }

    override fun onBind(data: AlbumPhotoViewData) {
        (itemView as ImageView).loadFromUrl(data.entity.url)
    }
}