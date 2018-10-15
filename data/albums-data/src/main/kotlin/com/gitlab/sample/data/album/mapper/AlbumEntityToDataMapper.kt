package com.gitlab.sample.data.album.mapper

import com.gitlab.sample.presentation.album.entities.AlbumData
import com.gitlab.sample.data.common.utils.Mapper
import com.gitlab.sample.domain.album.entities.AlbumEntity

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumEntityToDataMapper : Mapper<AlbumEntity, AlbumData>() {
    override fun mapFrom(from: AlbumEntity) = AlbumData(
            id = from.id,
            userId = from.userId,
            title = from.title
    )
}