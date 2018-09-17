package com.gitlab.sample.presentation.album_details.rmvvm

import android.support.annotation.StringRes
import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.presentation.common.ViewState

/**
 * Created by Hadi on 9/21/18.
 */
sealed class AlbumDetailsViewState : ViewState

class GetAlbumViewState(val photos: List<AlbumDetailsEntity>) : AlbumDetailsViewState()
class ErrorAlbumViewState(@StringRes val errorMessage: Int, val error: Throwable) : AlbumDetailsViewState()
class LoadingViewState(val loading: Boolean) : AlbumDetailsViewState()
