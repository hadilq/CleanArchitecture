package com.gitlab.sample.data.album.mapper

import com.gitlab.sample.presentation.album.entities.AlbumData
import com.gitlab.sample.data.common.utils.Mapper
import com.gitlab.sample.domain.album.entities.AlbumEntity

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumDataToEntityMapper : Mapper<AlbumData, AlbumEntity>() {
    override fun mapFrom(from: AlbumData) = AlbumEntity(
            id = from.id,
            userId = from.userId,
            title = from.title
    )
}