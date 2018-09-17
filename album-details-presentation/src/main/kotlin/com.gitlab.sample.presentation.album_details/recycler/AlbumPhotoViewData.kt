package com.gitlab.sample.presentation.album_details.recycler

import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.presentation.album_details.R
import com.gitlab.sample.presentation.common.ViewData

/**
 * Created by Hadi on 9/21/18.
 */
data class AlbumPhotoViewData(val entity: AlbumDetailsEntity): ViewData {
    override fun getType(): Int {
        return R.layout.photo
    }
}