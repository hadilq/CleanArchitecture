package com.gitlab.sample.presentation.album_details.rmvvm

import com.gitlab.sample.presentation.common.Action

/**
 * Created by Hadi on 9/28/18.
 */
sealed class AlbumDetailsAction : Action

object GetAlbumDetailsAction : AlbumDetailsAction()