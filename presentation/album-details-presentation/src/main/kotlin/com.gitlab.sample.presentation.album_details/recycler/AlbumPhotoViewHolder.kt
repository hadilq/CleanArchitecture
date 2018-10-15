package com.gitlab.sample.presentation.album_details.recycler

import android.view.View
import android.widget.ImageView
import com.gitlab.sample.presentation.common.BaseViewHolder
import com.gitlab.sample.presentation.common.extention.loadFromUrl

/**
 * Created by Hadi on 9/21/18.
 */
class AlbumPhotoViewHolder(view: View, private val itemWidth: Int = 0) : BaseViewHolder<AlbumPhotoViewData>(view) {

    init {
        itemView.layoutParams.height = itemWidth
        itemView.layoutParams = itemView.layoutParams
    }

    override fun onBind(data: AlbumPhotoViewData) {
        (itemView as ImageView).loadFromUrl(data.entity.url)
    }
}