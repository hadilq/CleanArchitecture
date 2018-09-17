package com.gitlab.sample.presentation.album_details.rmvvm

import com.gitlab.sample.domain.album_details.entities.AlbumDetailsEntity
import com.gitlab.sample.domain.album_details.usecases.GetAlbumDetails
import com.gitlab.sample.presentation.album_details.R
import com.gitlab.sample.presentation.common.BaseViewModel

/**
 * Created by Hadi on 9/21/18.
 */
class AlbumDetailsViewModel(private val useCase: GetAlbumDetails) : BaseViewModel() {

    // After disabling "Always up to date data" feature of LiveData(see BaseViewModel), because of its
    // downsides(Just imagine multi page GetAlbumViewState), we have to add this variable to keep the data
    // on configuration change.
    val savedDetails = mutableListOf<AlbumDetailsEntity>()
    var albumId: Long = 0

    fun getAlbumDetails() = addDisposable(useCase.observe(albumId)
            .doOnSubscribe { viewState.value = LoadingViewState(true) }
            .doOnComplete { viewState.value = LoadingViewState(false) }
            .doOnError { viewState.value = LoadingViewState(false) }
            .doOnDispose { viewState.value = LoadingViewState(false) }
            .map { list -> savedDetails.addAll(list); list }
            .map { GetAlbumViewState(it) as AlbumDetailsViewState }
            .onErrorReturn { ErrorAlbumViewState(R.string.error_happened, it) }
            .subscribe { viewState.value = it })
}