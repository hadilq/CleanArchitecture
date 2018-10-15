package com.gitlab.sample.data.album.mapper

import com.gitlab.sample.data.common.api.entities.AlbumDto
import com.gitlab.sample.data.common.utils.Mapper
import com.gitlab.sample.domain.album.entities.AlbumEntity

/**
 * Created by Hadi on 9/18/18.
 */
class AlbumDtoToEntityMapper : Mapper<AlbumDto, AlbumEntity>() {
    override fun mapFrom(from: AlbumDto) = AlbumEntity(
            id = from.id,
            userId = from.userId,
            title = from.title
    )
}