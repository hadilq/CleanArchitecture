package com.gitlab.sample.presentation.album_details.rmvvm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.presentation.album_details.di.AlbumDetailsScope
import javax.inject.Inject

/**
 * Created by Hadi on 9/21/18.
 */
@AlbumDetailsScope
class AlbumDetailsViewModelFactory @Inject constructor(
        private val getAlbums: GetAlbumDetails
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        /*If T is not an AlbumViewModel log an assertion because it should never happen*/
        return AlbumDetailsViewModel(getAlbums) as T
    }
}